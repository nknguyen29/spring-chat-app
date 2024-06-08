import React from 'react';
import { Link } from 'react-router-dom';

const Header = ({ user }) => {
  return (
    <header className="header">
      <Link to="/"><h1>Chat App</h1></Link>
      {user && <div>Welcome, {user}!</div>}
    </header>
  );
};

export default Header;