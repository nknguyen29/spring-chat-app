import React, { useContext, useState, useEffect } from "react";
import { Route, Routes, Navigate, Outlet } from 'react-router-dom';

import {
  StompSessionProvider,
} from "react-stomp-hooks";

import { jwtDecode } from 'jwt-decode';



import Header from "./components/Header";
import Sidebar from "./components/Sidebar";
import { ThemeProvider } from "./components/ThemeProvider";

import Debugging from "./components/Debugging";
import UserList from "./components/UserList";
import Discussion from "./components/Discussion";
import MyChatrooms from "./components/MyChatrooms";
import Join from "./components/Join";
import Login from "./components/Login";
import Logout from "./components/Logout";
import { AuthContext, AuthProvider } from "./components/AuthContext";
import ChatroomList from "./components/ChatroomList";
import StompListener from "./components/StompListener";

const PrivateWrapper = () => {
  const { isAuthenticated } = useContext(AuthContext);
  return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
};

export default function App() {
  const [user, setUser] = useState(null);
  // will be used to store the user's decoded token
  // which contains the user's id, email (sub), firstName, LastName, etc. 
  // payload : sub, iss, iat, exp, id, firstName, lastName, isAdmin, isLocked

  useEffect(() => {
    const token = sessionStorage.getItem('token');
    if (token) {
      const decodedToken = jwtDecode(token);
      setUser(decodedToken);
    }
  }
  , [setUser]);

  const [messages, setMessages] = useState({});
  // messages is an object where each key is a room ID 
  // and each value is an array of messages for that room. 
  // When a new message is received, it's added to the array for the corresponding room. 
  // If the room doesn't exist in the messages object yet, 
  // it's created with an empty array before the message is added.

  return (
    // Wrap the entire app in the AuthProvider, so that the AuthContext is available to all child components.
    <AuthProvider> 
      {/* Initialize Stomp connection */}
      {/* The Connection can be used by all child components via the hooks or hocs. */}
      <StompSessionProvider
        url={"http://localhost:8080/ws"}
        //All options supported by @stomp/stompjs can be used here
        debug={(str) => {
          console.log(str);
        }}
      >
        <StompListener user={user} setMessages={setMessages} />

          <ThemeProvider defaultTheme="light" storageKey="vite-ui-theme">
          <div className={`grid min-h-screen w-full ${user ? 'md:grid-cols-[220px_1fr] lg:grid-cols-[280px_1fr]' : ''}`}>
                {/* Only render the sidebar when the user is authenticated. */}
                {user && <Sidebar user = {user} />}

                <div className="flex flex-col">
                  {user && <Header user = {user} />}

                  <main className="flex flex-1 flex-col gap-4 p-4 lg:gap-6 lg:p-6">
                    <Routes>
                      <Route path="/" element={<Login setUser={setUser} />} />
                      <Route path="/login" element={<Login setUser={setUser} />} />

                      <Route element={<PrivateWrapper />}>
                        <Route path="/debugging" element={<Debugging />} />
                      </Route>
                      <Route element={<PrivateWrapper />}>
                        <Route path="/users" element={<UserList />} />
                      </Route>
                      <Route element={<PrivateWrapper />}>
                        <Route path="/chatrooms" element={<ChatroomList />} />
                      </Route>
                      <Route element={<PrivateWrapper />}>
                        <Route path="/my-chatrooms" element={<MyChatrooms user = {user} />} />
                      </Route>
                      <Route element={<PrivateWrapper />}>
                        <Route path="/discussion/:roomId" element={<Discussion user = {user}
                                                                              messages={messages} />} />
                      </Route>
                      <Route element={<PrivateWrapper />}>
                        <Route path="/join" element={<Join />} />
                      </Route>
                      <Route element={<PrivateWrapper />}>
                        <Route path="/logout" element={<Logout setUser={setUser} />} />
                      </Route>
                    </Routes>
                  </main>
                </div>
            </div>

          </ThemeProvider>
        
      </StompSessionProvider>
    </AuthProvider>
  );
}