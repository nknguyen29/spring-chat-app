import React, { useContext } from 'react';
import { AuthContext } from './AuthContext';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Logout = () => {
  const navigate = useNavigate();

  const { setIsAuthenticated } = useContext(AuthContext);
  
  // Clear the token from storage
  sessionStorage.removeItem('token');

  // Remove the Authorization header
  delete axios.defaults.headers.common['Authorization'];

  // Update the authentication state
  setIsAuthenticated(false);

  // Redirect to the login page
  navigate('/');


  return (
    <div>
        <h1>Logging out, please wait</h1>
    </div>
  );
};

export default Logout;