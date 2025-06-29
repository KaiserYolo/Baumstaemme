import WoodBox from "../components/WoodBox.jsx";
import {useState} from "react";
import '../App.css';

export default function GameSelectionPage({onGameSelection}) {
    const [gameName, setGameName] = useState("");
    const [mapSize, setMapSize] = useState("");
    const [nameError, setNameError] = useState("");
    const [selectedId, setSelectedId] = useState(null);

    const [gameList, setGameList] = useState(
        [
            {
                "id": 1,
                "name": "Server 1",
                "created": "2025-06-29T19:11:53.301+00:00",
                "status": "CREATED",
                "mapId": 1,
                "playerIdList": null,
                "mapSize": 0
            },
            {
                "id": 2,
                "name": "Server 1",
                "created": "2025-06-29T19:11:57.160+00:00",
                "status": "CREATED",
                "mapId": 2,
                "playerIdList": null,
                "mapSize": 0
            },
            {
                "id": 3,
                "name": "Server 1",
                "created": "2025-06-29T19:11:57.827+00:00",
                "status": "CREATED",
                "mapId": 3,
                "playerIdList": null,
                "mapSize": 0
            }
            ,
            {
                "id": 4,
                "name": "Server 1",
                "created": "2025-06-29T19:11:57.827+00:00",
                "status": "CREATED",
                "mapId": 3,
                "playerIdList": null,
                "mapSize": 0
            }
            ,
            {
                "id": 5,
                "name": "Server 1",
                "created": "2025-06-29T19:11:57.827+00:00",
                "status": "CREATED",
                "mapId": 3,
                "playerIdList": null,
                "mapSize": 0
            }
            ,
            {
                "id": 6,
                "name": "Server 1",
                "created": "2025-06-29T19:11:57.827+00:00",
                "status": "CREATED",
                "mapId": 3,
                "playerIdList": null,
                "mapSize": 0
            }
            ,
            {
                "id": 7,
                "name": "Server 1",
                "created": "2025-06-29T19:11:57.827+00:00",
                "status": "CREATED",
                "mapId": 3,
                "playerIdList": null,
                "mapSize": 0
            }
            ,
            {
                "id": 8,
                "name": "Server 1",
                "created": "2025-06-29T19:11:57.827+00:00",
                "status": "CREATED",
                "mapId": 3,
                "playerIdList": null,
                "mapSize": 0
            }
            ,
            {
                "id": 9,
                "name": "Server 9",
                "created": "2025-06-29T19:11:57.827+00:00",
                "status": "CREATED",
                "mapId": 3,
                "playerIdList": null,
                "mapSize": 0
            }
        ]
    );

    const createGame = () => {
        if (gameName !== "" && gameName !== null && mapSize !== "" && gameName !== "") {
            onGameSelection();
        }
        else{
            setNameError("Something went wrong!");
        }
    }

    const joinGame = () => {
            onGameSelection();
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
                    <button className="join-button" type="submit" onClick={createGame}>
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
                                />
                                <strong>{game.name}</strong> — {new Date(game.created).toLocaleString()}
                            </div>
                        ))}
                    </div>
                    <button className="join-button" type="submit" onClick={joinGame}>
                        <WoodBox n={8} text="Join Game" />
                    </button>
                </form>
            </div>
        </div>
    )
}