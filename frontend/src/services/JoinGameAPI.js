export const joinGameAPI = async (gameId) => {
    console.log(gameId);
    try{
        const url = new URL(`http://localhost:8080/api/games/${gameId}/join`);
        const response = await fetch(url, {
            method: 'PUT',
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

export const getAllGames = async () => {
    try{
        const url = new URL(`http://localhost:8080/api/games`);
        const response = await fetch(url, {
            method: 'GET',
            credentials: "include",
        })
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const json = await response.json()
        console.log("JoinGameAPI.js: All games received:", json);
        return json;
    } catch(error) {
        console.error("JoinGameAPI.js: Error in getAllGames:", error);
        throw error;
    }
}