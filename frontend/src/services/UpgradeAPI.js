export const upgrade = async (id, building) => {
    console.log(id);
    console.log(JSON.stringify({building}));
    try {
        const url = new URL(`http://localhost:8080/api/trees/${id}/upgrade`);
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({building}),
            credentials: "include"
        })
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const json = await response.json()
        return json;
    } catch (error) {
        console.log(error);
    }
}