import MapView from "./MapView.jsx";
import '../Map.css'

export default function GamePage() {
    return (
        <div>
            <div className="main-content" >
              <div className='Map'>
                <MapView/>
            </div>
        </div>
    );
}