import React, { useState } from 'react';
import { login } from './authApi';

function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = await login(username, password);
            localStorage.setItem('token', token);
            alert('Login erfolgreich!');
            // z. B. redirect zu /lobby
        } catch (err) {
            alert('Login fehlgeschlagen');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Benutzername</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </div>
            <div>
                <label>Passwort</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>
            <button type="submit">Login</button>
        </form>
    );
}

export default LoginForm;
