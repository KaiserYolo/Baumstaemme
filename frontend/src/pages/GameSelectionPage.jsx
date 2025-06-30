import WoodBox from "../components/WoodBox.jsx";
import {useEffect, useState} from "react";
import '../App.css';
import {getAllGames, joinGameAPI} from "../services/JoinGameAPI.js";
import {createGameAPI} from "../services/CreateGameAPI.js";

export default function GameSelectionPage({onGameSelection}) {
    const [gameName, setGameName] = useState("");
    const [mapSize, setMapSize] = useState("");
    const [nameError, setNameError] = useState("");
    const [selectedId, setSelectedId] = useState("");
    const [gameList, setGameList] = useState([]);

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);

    const getGames = async () => {
        setLoading(true);
        setError(null);
        try{
            const data = await getAllGames();
            setGameList(data);
        }
        catch(err){
            console.error("Error getting game list", err);
            setError(true);
        }finally{
            setLoading(false);
        }
    }

    useEffect(() => {
        getGames();
    }, []);

    const joinGame = async (id = selectedId) => {
        if(id !== ""){
            try{
                console.log("JoinGameAPI started");
                await joinGameAPI(id);
                console.log("JoinGameAPI was successful");
                onGameSelection();
            }catch(err){
                console.error("Error creating game", err);
            }
        }
    }

    const createGame = async () => {
        if (gameName !== "" && gameName !== null && mapSize !== "") {
            setNameError("");
            try{
                const returnJson = await createGameAPI(gameName, mapSize);
                console.log("create was successful ",returnJson);
                await getGames();
                setSelectedId(returnJson.id);
                console.log("selectedId ", selectedId);
                await joinGame(returnJson.id);
            }
            catch(err){
                console.error("Error creating game", err);
            }
        }
        else{
            setNameError("Something went wrong!");
        }
    }

    if(loading){
        return(
            <div className="main-content">
                <div className="game-selection">
                    <form className="game-selection-half">
                        <label className="game-selection-label">
                            Create Game
                        </label>
                        <div className="game-selection-create-params">
                            <a className="form-error">{nameError}</a>
                            <div className="input-group">
                                <label htmlFor="gamename">Name</label>
                                <input id="gamename" onChange={e => setGameName(e.target.value)} value={gameName} type="text" placeholder="Name your game" required={true} />
                            </div>

                            <div className="input-group">
                                <label htmlFor="mapsize">Size</label>
                                <input id="mapsize" onChange={e => setMapSize(e.target.value)} value={mapSize} type="number" placeholder="Set map size" required={true} min={10} max={500}/>
                            </div>
                        </div>
                        <button className="join-button" type="button" onClick={createGame}>
                            <WoodBox n={8} text="Create Game" />
                        </button>
                    </form>
                    <form className="game-selection-half">
                        <label className="game-selection-label">
                            Join Game
                        </label>
                        <div className="game-selection-game-list">
                            Loaging Games...
                        </div>
                        <button className="join-button" type="button" onClick={joinGame}>
                            <WoodBox n={8} text="Join Game" />
                        </button>
                    </form>
                </div>
            </div>
        );
    }

    if(error && !gameList){
        return(
            <div className="main-content">
                <div className="game-selection">
                    <form className="game-selection-half">
                        <label className="game-selection-label">
                            Create Game
                        </label>
                        <div className="game-selection-create-params">
                            <a className="form-error">{nameError}</a>
                            <div className="input-group">
                                <label htmlFor="gamename">Name</label>
                                <input id="gamename" onChange={e => setGameName(e.target.value)} value={gameName} type="text" placeholder="Name your game" required={true} />
                            </div>

                            <div className="input-group">
                                <label htmlFor="mapsize">Size</label>
                                <input id="mapsize" onChange={e => setMapSize(e.target.value)} value={mapSize} type="number" placeholder="Set map size" required={true} min={10} max={500}/>
                            </div>
                        </div>
                        <button className="join-button" type="button" onClick={createGame}>
                            <WoodBox n={8} text="Create Game" />
                        </button>
                    </form>
                    <form className="game-selection-half">
                        <label className="game-selection-label">
                            Join Game
                        </label>
                        <div className="game-selection-game-list">
                            An error occured loading the games...
                            Please try again!
                        </div>
                        <button className="join-button" type="button" onClick={joinGame}>
                            <WoodBox n={8} text="Join Game" />
                        </button>
                    </form>
                </div>
            </div>
        );
    }

    return (
        <div className="main-content">
            <div className="game-selection">
                <form className="game-selection-half">
                    <label className="game-selection-label">
                        Create Game
                    </label>
                    <div className="game-selection-create-params">
                        <a className="form-error">{nameError}</a>
                        <div className="input-group">
                            <label htmlFor="gamename">Name</label>
                            <input id="gamename" onChange={e => setGameName(e.target.value)} value={gameName} type="text" placeholder="Name your game" required={true} />
                        </div>

                        <div className="input-group">
                            <label htmlFor="mapsize">Size</label>
                            <input id="mapsize" onChange={e => setMapSize(e.target.value)} value={mapSize} type="number" placeholder="Set map size" required={true} min={10} max={500}/>
                        </div>
                    </div>
                    <button className="join-button" type="button" onClick={createGame}>
                        <WoodBox n={8} text="Create Game" />
                    </button>
                </form>
                <form className="game-selection-half">
                    <label className="game-selection-label">
                        Join Game
                    </label>
                    <div className="game-selection-game-list">
                        {gameList.map((game)=> (
                            <div
                                key={game.id}
                                onClick={() => setSelectedId(game.id)}
                                className="list-entry"
                                style={{
                                    cursor: 'pointer',
                                    padding: '10px',
                                    margin: '5px 0',
                                    border: '1px solid #ccc',
                                    borderRadius: '4px',
                                    backgroundColor: selectedId === game.id ? '#d0f0fd' : '#fff',
                                    boxSizing: 'border-box',
                                }}
                            >
                                <input
                                    type="radio"
                                    name="server"
                                    value={game.id}
                                    checked={selectedId === game.id}
                                    onChange={() => setSelectedId(game.id)}
                                    style={{ marginRight: '10px' }}
                                    required={true}
                                />
                                <strong>{game.name}</strong> — {new Date(game.created).toLocaleString()}
                            </div>
                        ))}
                    </div>
                    <button className="join-button" type="button" onClick={joinGame}>
                        <WoodBox n={8} text="Join Game" />
                    </button>
                </form>
            </div>
        </div>
    )
}