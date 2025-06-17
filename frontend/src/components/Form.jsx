import {StrictMode, useState} from "react";
import {loginUser} from "../services/AuthAPI.js";
import '../App.css';
import WoodBox from "./WoodBox.jsx";

export default function Form(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [usernameError, setUsernameError] = useState("");
    const [passwordError, setPasswordError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        const buttonId = e.nativeEvent.submitter.id;
        setUsernameError("");
        setPasswordError("");


        if(buttonId === "login"){
           console.log("login attempt");
            const user = await loginUser({username, password});
        }
        else if(buttonId === "register"){
            console.log("register attempt");
            //const user = await registerUser({username, password});
        }
        else{
            console.log("error during login or register");
            setUsernameError("Something went wrong");
            setPasswordError("Something went wrong");
        }

    }

    return (
        <form onSubmit={handleSubmit} className="login-form">
            <div className="form-box">
                <h1 className="form-top">Login</h1>
                <div className="form-middle">
                    <a className="form-error">{usernameError}</a>
                    <div className="input-group">
                        <label htmlFor="username">Username</label>
                        <input id="username" onChange={e => setUsername(e.target.value)} value={username} type="text" placeholder="Username" required={true} />
                    </div>

                    <a className="form-error">{passwordError}</a>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input id="password" onChange={e => setPassword(e.target.value)} value={password} type="password" placeholder="Password" required={true} />
                    </div>
                </div>
                <div className="form-bottom">
                    <button type="submit" id="login" className="login-button" >
                        <WoodBox n={5} text="Login" className="header-title" />
                    </button>
                    <button type="submit" id="register" className="login-button" >
                        <WoodBox n={5} text="Register" className="header-title" />
                    </button>
                </div>
            </div>
        </form>
    );

}