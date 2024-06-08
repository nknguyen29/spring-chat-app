import React, { useState } from "react";
import { CssBaseline } from "@mui/material";
import { useParams } from 'react-router-dom';

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
        <Showcase title={"Incoming Messages"}><ListMessagesFromRoom /></Showcase>
        <Showcase title={"Sending Messages"}><SendingMessages /></Showcase>
      </Container>
    </StompSessionProvider>
  );
}



export function ListMessagesFromRoom() {
  const [messages, setMessages] = useState([]);
  const roomId = useParams().roomId;

  useSubscription("/topic/" + roomId, (message) => {
    setMessages([...messages, message.body]);
  });

  return (
    <Box>
      <Typography>Welcome, you are currently in the Room ID : {roomId}</Typography>
      <Typography>Messages received so far :</Typography>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>{message}</li>
        ))}
      </ul>
    </Box>
  );
}

export function SendingMessages() {
  const [input, setInput] = useState("");
  // take roomId from the URL of React Router
  const roomId = useParams().roomId;
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
      <Grid item><Typography>to Room ID : {roomId}</Typography></Grid>
      <Grid item>
        <Typography variant={"body1"}>
          Last Message received: {lastMessage}
        </Typography>
      </Grid>
    </Grid>
  );
}

export function Showcase(props) {
return (
    <Accordion style={{ margin: "3em" }} TransitionProps={{ unmountOnExit: true }} defaultExpanded>
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