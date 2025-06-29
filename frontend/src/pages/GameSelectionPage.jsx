import WoodBox from "../components/WoodBox.jsx";
import {useState} from "react";
import '../App.css';

export default function GameSelectionPage({onGameSelection}) {
    const [gameName, setGameName] = useState("");
    const [mapSize, setMapSize] = useState("");
    const [nameError, setNameError] = useState("");

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
                <div className="game-selection-half">
                    <label className="game-selection-label">
                        Join Game
                    </label>
                    <div>
                        Liste
                    </div>
                    <button className="join-button" onClick={joinGame}>
                        <WoodBox n={8} text="Join Game" />
                    </button>
                </div>
            </div>
        </div>
    )
}