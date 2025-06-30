import MapView from "./MapView.jsx";
import '../Map.css'

export default function GamePage({ gameId }) {

    return (
            <div className="main-content" >
              <div className='Map'>
                  <MapView gameId={gameId} />
            </div>
        </div>

    );
}