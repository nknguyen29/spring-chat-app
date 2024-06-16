import { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import { jwtDecode } from "jwt-decode";

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";

import { AuthContext } from "./AuthContext";

const Login = ({ setUser }) => {
  const navigate = useNavigate();
  const { isAuthenticated, setIsAuthenticated } = useContext(AuthContext);

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/");
    }
  }, [isAuthenticated, navigate]);
  
  const [email, setMail] = useState("");
  const [password, setPassword] = useState("");

  

  const handleLogin = (event) => {
    event.preventDefault();

    console.log("email = " + email);
    console.log("password = " + password);

    // Requete HTTP login au backend spring
    axios
      .post("http://localhost:8080/api/auth/login", {
        email: email,
        password: password,
      })
      .then((res) => {
        console.log("Response from backend : ", res);

        // Stock the token JWT dans le sessionStorage
        if (res.data.accessToken) {
          console.log("token = " + res.data.accessToken);
          sessionStorage.setItem("token", res.data.accessToken);
        }

        // Decode the token and set the user state
        const decodedToken = jwtDecode(res.data.accessToken);
        setUser(decodedToken);

        // Change isAuthenticated to true
        setIsAuthenticated(true);
        
        navigate("/");
      })
      .catch((err) => {
        if (err.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          console.log(err.response.data);
          console.log(err.response.status);
          console.log(err.response.headers);
          if (err.response.status === 400) {
            alert("Email or password does not follow the required format");
          } else if (err.response.status === 401) {
            alert("Email or password is incorrect");
          } else {
            alert("An unknown error occurred");
          }
        } else if (err.request) {
          // The request was made but no response was received
          console.log(err.request);
          alert("No response received from server, is the server running?");
        } else {
          // Something happened in setting up the request that triggered an Error
          console.log("Error", err.message);
          alert("An error occurred");
        }
      });
  };

  return (
    <Card className="mx-auto max-w-sm">
      <CardHeader>
        <CardTitle className="text-2xl">Login</CardTitle>
        <CardDescription>
          Enter your credentials to login to your account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="grid gap-4">
          <div className="grid gap-2">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              type="email"
              placeholder="email@example.com"
              required
              value={email}
              onChange={(e) => {
                setMail(e.target.value);
              }}
            />
          </div>
          <div className="grid gap-2">
            <div className="flex items-center">
              <Label htmlFor="password">Password</Label>
            </div>
            <Input
              id="password"
              type="password"
              required
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            />
          </div>
          <Button type="submit" className="w-full" onClick={handleLogin}>
            Login
          </Button>
          <Button variant="outline" className="w-full">
            Forgot Password
          </Button>
        </div>
      </CardContent>
    </Card>
  );
};

export default Login;
