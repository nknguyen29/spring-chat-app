import { useState, useEffect } from "react";
import axios from "axios";

function useGetAllUsers() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("/api/users/public")
      .then((response) => {
        setUsers(response.data);
      })
      .catch((error) => {
        setError(error.message);
      });
  }, []);

  return { users, error };
}

export default useGetAllUsers;
