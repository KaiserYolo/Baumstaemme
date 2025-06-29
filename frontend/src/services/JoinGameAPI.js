export const joinGameAPI = async (gameId) => {
    try{
        const url = new URL(`http://localhost:8080/api/games/${gameId}/join`);
        const response = await fetch(url, {
            method: 'PUT',
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