import React, { useState } from "react";
import { CssBaseline } from "@mui/material";

import {
  StompSessionProvider,
  useStompClient,
  useSubscription,
} from "react-stomp-hooks";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Button,
  Card,
  CardContent,
  Container,
  Grid,
  TextField,
  Typography
} from "@mui/material";

import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

export default function App() {
  return (
    //Initialize Stomp connection
    //The Connection can be used by all child components via the hooks or hocs.
    <StompSessionProvider
      url={"http://localhost:8080/ws"}
      //All options supported by @stomp/stompjs can be used here
      debug={(str) => {
        console.log(str);
      }}
    >
      <CssBaseline />
      <Container>
        <Card style={{ margin: "3em" }} variant="outlined">
          <CardContent>
            <Typography>STOMP Messages</Typography>
          </CardContent>
        </Card>
        <Showcase title={"Subscribing"}><Subscribing /></Showcase>
        <Showcase title={"Sending Messages"}><SendingMessages /></Showcase>
        <Showcase title={"Dynamic subscribing/unsubscribing"}><DynamicSubscription /></Showcase>
      </Container>
    </StompSessionProvider>
  );
}

export function Subscribing() {
  const [roomId, setRoomId] = useState("");
  const [lastMessage, setLastMessage] = useState("No message received yet");

  useSubscription("/topic/" + roomId, (message) => setLastMessage(message.body));
  
  return (
    <> <Grid container direction="row" spacing={3}>
      <Grid item><Typography>Subscribe to Room ID:</Typography></Grid>
      <Grid item><TextField variant="standard" value={roomId}
        onChange={(event => setRoomId(event.target.value))} /></Grid>
    </Grid><Box>Last Message: {lastMessage}</Box> </>
  );
}


export function SendingMessages() {
  const [input, setInput] = useState("");
  const [roomId, setRoomId] = useState("");
  const [lastMessage, setLastMessage] = useState("No message received yet");

  //Get Instance of StompClient
  //This is the StompCLient from @stomp/stompjs
  //Note: This will be undefined if the client is currently not connected
  const stompClient = useStompClient();
  //echo reply 
  useSubscription("/topic/" + roomId, (message) => setLastMessage(message.body));

  const sendMessage = () => {
    if(stompClient) {
      //Send Message
      stompClient.publish({
        destination: "/app/chat/" + roomId,
        body: JSON.stringify({'name': input})
      });
    }
    else {
      //Handle error
    }
  };

  return (
    <Grid container direction="row" spacing={3}>
      <Grid item><Button variant={"contained"} onClick={sendMessage}>Send Message</Button></Grid>
      <Grid item><TextField variant="standard" value={input}
                            onChange={(event => setInput(event.target.value))} /></Grid>
      <Grid item><Typography>to Room ID:</Typography></Grid>
      <Grid item><TextField variant="standard" value={roomId} 
                            onChange={(event => setRoomId(event.target.value))} /></Grid>  
      <Grid item>
        <Typography variant={"body1"}>
          Last Message received: {lastMessage}
        </Typography>
      </Grid>
    </Grid>
  );
}

export function DynamicSubscription() {
  const [lastMessage, setLastMessage] = useState("No message received yet");
  const [subscribed, setSubscribed] = useState(false);
  const [roomId, setRoomId] = useState("");

  useSubscription(
    //The value of the first parameter can be mutated to dynamically subscribe/unsubscribe from topics
    subscribed ? ["/topic/"] + roomId : [],
    (message) => setLastMessage(message.body)
  );

  return (
    <Box sx={{width: '100%', display: 'flex', justifyContent: 'space-between'}}>
      <Box>Subscribe to Room ID:</Box>
      <Box><TextField variant="standard" value={roomId}
                      onChange={(event => setRoomId(event.target.value))}></TextField></Box>
      
      <Box>Last Message: {lastMessage}</Box>
      <Box>
        <Button onClick={() => setSubscribed(!subscribed)}>{subscribed ? "Unsubscribe" : "Subscribe"}</Button>
      </Box>
    </Box>
  );
}

export function Showcase(props) {
  return (
    <Accordion style={{ margin: "3em" }} TransitionProps={{ unmountOnExit: true }}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon />}
        aria-controls="panel1a-content"
        id="panel1a-header"
      >
        <Typography>{props.title}</Typography>
      </AccordionSummary>
      <AccordionDetails>
        {props.children}
      </AccordionDetails>
    </Accordion>
  );
}