const API_BASE = 'http://localhost:8080/api/auth'; // ggf. anpassen

export async function login(username, password) {
    const response = await fetch(`${API_BASE}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
        throw new Error('Login fehlgeschlagen');
    }

    const data = await response.json();
    return data.token; // JWT vom Backend
}

export async function register(username, password) {
    const response = await fetch(`${API_BASE}/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Registrierung fehlgeschlagen: ${errorText}`);
    }

    return await response.json();
}

export async function getProfile(token) {
    const response = await fetch(`${API_BASE}/me`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error('Profil konnte nicht geladen werden');
    }

    return await response.json();
}
