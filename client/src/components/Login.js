import { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import { AuthContext } from './AuthContext';

const Login = (props) => {
    const navigate = useNavigate();

    const { isAuthenticated, setIsAuthenticated } = useContext(AuthContext);
    useEffect(() => {
        if (isAuthenticated) {
            // go to the main page
            navigate('/discussion/MainRoom');
        }
    }, [isAuthenticated, navigate]);

    const [email, setMail] = useState('')
    const [password, setPassword] = useState('')

    const handleLogin = (event) => {
        event.preventDefault();

        console.log("email = " + email)
        console.log("password = " + password)

        // Requete HTTP login au backend spring
        axios.post("http://localhost:8080/api/auth/login", {email: email, password: password})
            .then(res => {
                console.log("Response from backend : ", res)

                // Stock the token JWT dans le sessionStorage
                if (res.data.accessToken){
                    console.log("token = " + res.data.accessToken)
                    sessionStorage.setItem("token", res.data.accessToken)
                }

                // Change isAuthenticated to true
                setIsAuthenticated(true);
                
            })
            .catch(err => {
                if (err.response) {
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2xx
                    console.log(err.response.data);
                    console.log(err.response.status);
                    console.log(err.response.headers);
                    if(err.response.status === 400){
                        alert('Email or password does not follow the required format');
                    } else if(err.response.status === 401){
                        alert('Email or password is incorrect');
                    } else {
                        alert('An unknown error occurred');
                    }
                } else if (err.request) {
                    // The request was made but no response was received
                    console.log(err.request);
                    alert('No response received from server, is the server running?');
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', err.message);
                    alert('An error occurred');
                }
            })
    }

    return (
        <div className="login-container">
            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">Email</label>
                    <input type="email" name="email" className="form-control" id="email" value={email}
                           onChange={e => {
                               setMail(e.target.value)
                           }} required={true}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input type="password" name="password" className="form-control" id="password" value={password}
                           onChange={e => {
                               setPassword(e.target.value)
                           }} required={true}/>
                </div>
                <button type="submit" className="btn btn-primary w-100" onClick={handleLogin}>Connexion</button>
                <br></br>
            </form>
        </div>
    );
}

export default Login;
