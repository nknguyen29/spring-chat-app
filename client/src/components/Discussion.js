import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { useStompClient } from "react-stomp-hooks";
import { TextField, Button, List, ListItem, Typography, Grid, Card, CardContent } from '@mui/material';

import useGetAllUsers from '../hooks/useGetAllUsers';

export default function Discussion({ user, messages }) {
  const { roomId } = useParams();
  const [input, setInput] = useState("");

  // Get Instance of StompClient
  const stompClient = useStompClient();


  const sendMessage = () => {
    if(stompClient) {
      // Send Message
      stompClient.publish({
        destination: "/app/chat/" + roomId,
        body: JSON.stringify({
          'sender' : user.id,
          'content': input,
        }) 

      });
    }
    else {
      // Handle error
      console.error('Stomp client is not connected');
    }
  };

  // Get the messages for the current room
  const roomMessages = (messages[roomId] || []).map(message => JSON.parse(message));

  console.log('Room messages:', roomMessages);

  // Fetch all users
  const {users, error} = useGetAllUsers();
  if (error) {
    return <div>Error: {error}</div>;
  }
  console.log('Users:', users);

  return (
    <div>
      <Typography variant="h4">Room {roomId}</Typography>
      <List>
        {roomMessages.map((message, index) => {

          // Find the sender's name
          const sender = users.find(user => user.id === message.sender);
          const senderName = sender ? sender.firstName + ' ' + sender.lastName : 'Unknown Sender';

          // Check if the message is from the current user
          const isCurrentUser = message.sender === user.id;

          return (
            <ListItem key={index}>
              <Card variant="outlined" sx={{ width: '84%', backgroundColor: isCurrentUser ? '#e0f7fa' : '#fff59d', mb: 1 }}>
                <CardContent sx={{ display: 'flex', flexDirection: 'column' }} >
                  <Typography 
                    variant="subtitle1" 
                    color="text.secondary" 
                    align={isCurrentUser ? 'right' : 'left'}>
                      {isCurrentUser ? 'Me' : senderName}
                  </Typography>
                  <Typography 
                  variant="body2"
                  align={isCurrentUser ? 'right' : 'left'}>
                    {message.content}
                  </Typography>
                </CardContent>
              </Card>
            </ListItem>
          );
          })}
      </List>
      <Grid container spacing={1}>
        <Grid item xs={10}>
          <TextField
            value={input}
            onChange={e => setInput(e.target.value)}
            placeholder="Message"
            fullWidth
            sx={{ height: "100%" }}
          />
        </Grid>
        <Grid item xs={2}>
          <Button 
            variant="contained" 
            color="primary" 
            onClick={sendMessage} 
            fullWidth 
            sx={{ height: "100%" }}
          >
            Send
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}