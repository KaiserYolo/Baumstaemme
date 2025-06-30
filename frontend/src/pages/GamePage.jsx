import MapView from "./MapView.jsx";
import '../Map.css'
import TileMenu from "../components/TileMenu.jsx";
import WoodBox from "../components/WoodBox.jsx";

export default function GamePage() {

    return (
            <div className="main-content" >
              <div className='Map'>
                  <MapView/>
            </div>
        </div>

    );
}