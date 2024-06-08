import { useContext, useEffect } from 'react';
import { AuthContext } from './AuthContext';
import axios from 'axios';

const Logout = () => {
  const { setIsAuthenticated } = useContext(AuthContext);

  useEffect(() => {
    // Clear the token from storage
    sessionStorage.removeItem('token');

    // Remove the Authorization header
    delete axios.defaults.headers.common['Authorization'];

    // Update the authentication state
    setIsAuthenticated(false);
  }, [setIsAuthenticated]); 

  return null; 
};

export default Logout;