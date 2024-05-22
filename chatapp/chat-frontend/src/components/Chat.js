import React, { useEffect, useState } from 'react';

function Chat() {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const [wsError, setWsError] = useState(null);
    let socket;

    useEffect(() => {
        try {
            socket = new WebSocket('ws://localhost:8080/chat');
            
            socket.onopen = () => {
                console.log('Connected to the chat server');
                setWsError(null);
            };

            socket.onmessage = (event) => {
                const message = event.data;
                setMessages((prevMessages) => [...prevMessages, message]);
            };

            socket.onerror = (error) => {
                console.error('WebSocket error:', error);
                setWsError('WebSocket connection failed');
            };

            socket.onclose = () => {
                console.log('WebSocket connection closed, hmmm pkoi?');
                setWsError('WebSocket connection closed, hmmm pkoi?');
            };
        } catch (error) {
            console.error('WebSocket initialization failed:', error);
            setWsError('WebSocket initialization failed');
        }

        return () => {
            if (socket) {
                socket.close();
            }
        };
    }, []);

    const sendMessage = () => {
        if (socket && socket.readyState === WebSocket.OPEN) {
            socket.send(input);
            setInput('');
        } else {
            console.error('WebSocket is not open');
        }
    };

    return (
        <div>
            <h1>Chat</h1>
            {wsError && <p style={{ color: 'red' }}>{wsError}</p>}
            <div id="chat-window">
                {messages.map((msg, index) => (
                    <div key={index}>{msg}</div>
                ))}
            </div>
            <input 
                type="text" 
                value={input} 
                onChange={(e) => setInput(e.target.value)} 
                placeholder="Type a message..." 
            />
            <button onClick={sendMessage}>Send</button>
        </div>
    );
}

export default Chat;
