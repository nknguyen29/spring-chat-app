import React, { useContext } from "react";
import { CssBaseline } from "@mui/material";
import { Route, Routes, Navigate, Outlet } from 'react-router-dom';

import {
  StompSessionProvider,
} from "react-stomp-hooks";

import Header from "./components/Header";
import Sidebar from "./components/Sidebar";

import ChatList from "./components/ChatList";
import Debugging from "./components/Debugging";
import UserList from "./components/UserList";
import Discussion from "./components/Discussion";
import Join from "./components/Join";
import Login from "./components/Login";
import Logout from "./components/Logout";
import { AuthContext, AuthProvider } from "./components/AuthContext";

const PrivateWrapper = () => {
  const { isAuthenticated } = useContext(AuthContext);
  return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
};

export default function App() {
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
        <div className="app">
          <CssBaseline />
          <Header />
          <div className="layout">
            <div className="sidebar">
              <Sidebar />
            </div>

            <div className="main-content">
              <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/login" element={<Login />} />

                <Route element={<PrivateWrapper />}>
                  <Route path="/chats" element={<ChatList />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/debugging" element={<Debugging />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/users" element={<UserList />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/discussion/:roomId" element={<Discussion />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/join" element={<Join />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/logout" element={<Logout />} />
                </Route>
              </Routes>
            </div>
          </div>
        </div>
        
      </StompSessionProvider>
    </AuthProvider>
  );
}