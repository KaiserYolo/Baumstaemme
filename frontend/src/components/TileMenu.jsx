import React from 'react';

const TileMenu = ({ tileData, onClose }) => {
    if (!tileData) return null;

    return (
        <div className="tile-menu">
            <h3>Kachel-Information</h3>
            <p>ID: {tileData.id}</p>
            <p>Position: (X: {tileData.xcoordinate}, Y: {tileData.ycoordinate})</p>
            <p>Typ: {tileData.type}</p>
            <button onClick={onClose}>Schließen</button>
        </div>
    );
};

export default TileMenu;