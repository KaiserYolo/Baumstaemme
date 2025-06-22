export const getMap = async (id) => {
    try {
        const url = new URL('http://localhost:8080/api/map/getMap')
        url.searchParams.append('id', id.toString());
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