import React from "react";
import { Sidebar, Menu, MenuItem, SubMenu } from 'react-pro-sidebar';
import { Link } from 'react-router-dom';
import LogoutButton from './Logout';

export default function App() {
  return (
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
                <MenuItem component={<Link to="/logout" />}> Logout </MenuItem>
                </Menu>
            </Sidebar>
        </div>

  );
}