export const getTree = async (id) => {
    try {
        const url = new URL(`http://localhost:8080/api/trees/${id}`)
        const response = await fetch(url, {
            method: 'GET',
            credentials: "include",
        })
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const json = await response.json()
        console.log("Ich bin ein Baum in der Tree API" +json);
        return json;
    } catch (error) {
        console.log(error);
    }
}