import MapView from "./MapView.jsx";
import '../Map.css'
import TileMenu from "../components/TileMenu.jsx";
import WoodBox from "../components/WoodBox.jsx";
/*import {useState} from "react";*/

export default function GamePage() {
    /*Test, muss wieder entfernt werden*/
    /*
    const [selectedTile, setSelectedTile] = useState(null);
    const fillSelectedTile = () =>{
        setSelectedTile(
            {
                "id": 1,
                "size": 20,
                "tiles": [
                    {
                        "id": 20,
                        "position": {
                            "x": 0.0,
                            "y": 10.0
                        },
                        "type": "TREE",
                        "tree": {
                            "id": 20,
                            "name": "Baum",
                            "owner": null,
                            "leaves": 1210,
                            "leavesProduction": 10
                        }
                    }]
            }

        );
    }
    */
    return (
            <div className="main-content" >
              <div className='Map'>
                  {/*<button className="test-button" onClick={fillSelectedTile}>
                      <WoodBox n={5} text="Test" />
                  </button>
                  <TileMenu
                      tileData={selectedTile}
                      onClose={() => setSelectedTile(null)}
                  />*/}

                  <MapView/>
            </div>
        </div>

    );
}