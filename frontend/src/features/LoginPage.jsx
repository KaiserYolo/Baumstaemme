import {StrictMode, useState} from "react";
import {LoginText, SubmitButton, InputField} from "../components/LoginForm.jsx";
import {loginUser} from "../services/AuthAPI.js";


const LoginPage = () => {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("");


    const handleLogin = async (e) => {
        e.preventDefault();
        console.log(username);
        console.log(password);

        try {
            const user = await loginUser({username, password});
            console.log("erfolg")
        } catch (err) {
            setError("Login Fehlgeschlagen")
        }

    }

    return (
        <StrictMode>
            <form onSubmit={handleLogin}>
                <div style={styles.container}>
                    <LoginText />
                    <InputField
                    name={"username"}
                    type={"text"}
                    placeholder={"Username"}
                    required={true}
                    value={username}
                    onchange={(e) => setUsername(e.target.value)}
                    />
                    <InputField
                        name={"password"}
                        type={"password"}
                        placeholder={"Password"}
                        required={true}
                        value={password}
                        onchange={(e) => setPassword(e.target.value)}
                    />
                    <SubmitButton/>
                </div>
            </form>
        </StrictMode>
    );

}

const styles = {
    container: {
        width: '300px',
        margin: '100px auto',
        textAlign: 'center',
    },
}

export default LoginPage