export async function loginUser(credentials) {
    const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(credentials),
        credentials: "include",
    });

    if (!response.ok) {
        throw new Error('Login failed');
    }

    return response.json();
}

export async function registerUser(credentials) {
    const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(credentials),
        credentials: "include",
    });

    if (!response.ok) {
        throw new Error('Registration failed');
    }

    return response.json();
}
