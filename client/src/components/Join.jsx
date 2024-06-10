import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';

import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Container,
  Grid,
  TextField,
  Typography
} from "@mui/material";

import { Button } from "./ui/button.jsx";

import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

export default function App() {
  return (
    <><Container>
          <Showcase title={"Joining Room"}><JoiningRoom /></Showcase>
      </Container></>
  );
}

export function JoiningRoom() {
  // take roomId from the URL of React Router
  const [roomId, setRoomId] = useState("");

//   const stompClient = useStompClient();
  const navigate = useNavigate();

  const joinRoom = () => {
    navigate('/discussion/' + roomId);
  };

  return (
    <Grid container direction="row" spacing={3}>
      <Grid item><TextField variant="standard" value={roomId}
                      onChange={(event => setRoomId(event.target.value))}></TextField></Grid>
      <Grid item><Button variant="outline" onClick={joinRoom}>Join Room </Button></Grid>                     
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