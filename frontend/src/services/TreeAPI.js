export const getTree = async (id) => {
    try {
        const url = new URL(`http://localhost:8080/api/trees/${id}`)
        const response = await fetch(url, {
            method: 'GET',
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