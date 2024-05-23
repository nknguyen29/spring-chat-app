import React, { useEffect, useState } from 'react';

function Chat() {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const [wsError, setWsError] = useState(null);
    const [ws, setWs] = useState(null);

    useEffect(() => {
        const connectWebSocket = () => {
            const socket = new WebSocket('ws://localhost:8080/chat');

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
                console.log('WebSocket connection closed');
                setWsError('WebSocket connection closed');
                // Retry connection
                // setTimeout(connectWebSocket, 5000);
            };

            setWs(socket);
        };

        connectWebSocket();

        return () => {
            if (ws) {
                ws.close();
            }
        };
    }, [ws]);

    const sendMessage = () => {
        if (ws && ws.readyState === WebSocket.OPEN) {
            console.log('Sending message:', input);
            ws.send(input);
            setInput('');
        } else {
            console.error('WebSocket is not open');
            setWsError('WebSocket is not open');
        }
    };

    return (
        <div>
            <h1>Chat</h1>
            {wsError && <p style={{ color: 'red' }}>{wsError}</p>}
            <div id="chat-window">
                <h2>Received Messages</h2>
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