export const createGameAPI = async (name, mapSize) => {
    try{
        const url = new URL (`http://localhost:8080/api/games`);
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(name, mapSize),
            credentials: "include",
        })
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const json = await response.json()
        console.log(json);
        return json;

    } catch(error) {
        console.log(error);
    }

}