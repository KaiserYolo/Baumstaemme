export const getMap = async (id) => {
    console.log('Getting map data', id);
    try {
        const url = new URL(`http://localhost:8080/api/maps/${id}`)
        const response = await fetch(url, {
            method: 'GET',
            credentials: "include",
        })
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const json = await response.json()
        console.log(json);
        return json;
    } catch (error) {
        console.log(error);
    }
}