import React from "react";
import { Sidebar, Menu, MenuItem, SubMenu } from 'react-pro-sidebar';
import { Link } from 'react-router-dom';

import useGetChatrooms from '../hooks/useGetChatrooms';

export default function App({ user }) {
  const { chatrooms, error } = useGetChatrooms(user.id);

  return (
    <div className="sidebar">
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <Sidebar>
        <Menu>
          <SubMenu label="My Chatrooms">
            {user && chatrooms && chatrooms.map(chatroom => (
              <MenuItem component={<Link to={`/discussion/${chatroom.id}`} />}>
                {chatroom.title}
              </MenuItem>
            ))}
            <MenuItem component={<Link to="/join/" />}> Join Room </MenuItem>
          </SubMenu>

          <MenuItem component={<Link to="/my-chatrooms" />}> My Chatrooms' Details </MenuItem>
          <MenuItem component={<Link to="/users" />}> Show All Users </MenuItem>
          <MenuItem component={<Link to="/chatrooms" />}> Show All Chatrooms </MenuItem>
          <MenuItem component={<Link to="/chats" />}> Chat template from Prof </MenuItem>
          <MenuItem component={<Link to="/debugging" />}> Debugging </MenuItem>
          <MenuItem component={<Link to="/logout" />}> Logout </MenuItem>
        </Menu>
      </Sidebar>
    </div>
  );
}