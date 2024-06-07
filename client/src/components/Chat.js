import React from "react";
import { CssBaseline } from "@mui/material";
import { Sidebar, Menu, MenuItem, SubMenu } from 'react-pro-sidebar';
import {Link, Route, Routes } from 'react-router-dom';

import {
  StompSessionProvider,
} from "react-stomp-hooks";

import Header from "./Header";

import ChatList from "./ChatList";
import Debugging from "./Debugging";
import UserList from "./UserList";
import Discussion from "./Discussion";
import Join from "./Join";

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
      <div className="app">
        <CssBaseline />
        <Header />
        <div className="layout">
          <div className="sidebar">
            <Sidebar>
            <Menu>
                <SubMenu label="My Chatrooms">
                  <MenuItem component={<Link to="/discussion/MainRoom" />}> Main Room </MenuItem>
                  <MenuItem component={<Link to="/join/" />}> Join Room </MenuItem>
                </SubMenu>
                <MenuItem component={<Link to="/chats" />}> Chat template from Prof </MenuItem>
                <MenuItem component={<Link to="/debugging" />}> Debugging </MenuItem>
                <MenuItem component={<Link to="/users" />}> User List </MenuItem>
              </Menu>
            </Sidebar>
          </div>

          <div className="main-content">
            <Routes>
              <Route path="/chats" element={<ChatList />} />
              <Route path="/debugging" element={<Debugging />} />
              <Route path="/users" element={<UserList />} />
              <Route path="/discussion/:roomId" element={<Discussion />} />
              <Route path="/join" element={<Join />} />
            </Routes>
          </div>
        </div>
      </div>
      
    </StompSessionProvider>
  );
}