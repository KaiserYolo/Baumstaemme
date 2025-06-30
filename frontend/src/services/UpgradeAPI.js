export const upgrade = async (id, type) => {
    try {
        const url = new URL(`http://localhost:8080/api/trees/${id}/upgrade`);
        const response = await fetch(url, {
            method: 'POST',
            body: JSON.stringify({type}),
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