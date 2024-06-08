import React, { useContext, useState, useEffect } from "react";
import { CssBaseline } from "@mui/material";
import { Route, Routes, Navigate, Outlet } from 'react-router-dom';

import {
  StompSessionProvider,
} from "react-stomp-hooks";

import { jwtDecode } from 'jwt-decode';

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
import ChatroomList from "./components/ChatroomList";

const PrivateWrapper = () => {
  const { isAuthenticated } = useContext(AuthContext);
  return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
};

export default function App() {
  const [user, setUser] = useState(null);
  // will be used to store the user's name by Login and Logout

  useEffect(() => {
    const token = sessionStorage.getItem('token');
    if (token) {
      const decodedToken = jwtDecode(token);
      setUser(decodedToken.sub);
    }
  }
  , [setUser]);

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
          <Header user={user} />
          <div className="layout">

          {/* Only render the sidebar when the user is authenticated. */}
          {user && <Sidebar />}

            <div className="main-content">
              {/* Here are all the routes used in the app. */}
              {/* To change the order & routes used in the sidebar, go to components/Sidebar.js. */}
              <Routes>
                <Route path="/" element={<Login setUser={setUser} />} />
                <Route path="/login" element={<Login setUser={setUser} />} />

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
                  <Route path="/chatrooms" element={<ChatroomList />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/discussion/:roomId" element={<Discussion />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/join" element={<Join />} />
                </Route>
                <Route element={<PrivateWrapper />}>
                  <Route path="/logout" element={<Logout setUser={setUser} />} />
                </Route>
              </Routes>
            </div>
          </div>
        </div>
        
      </StompSessionProvider>
    </AuthProvider>
  );
}