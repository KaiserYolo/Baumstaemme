import React from 'react';
import WoodBox from "./WoodBox.jsx";
import '../App.css';
import {TileMenuComponent, TileMenuResourceComponent, TileMenuTitleComponent} from "./TileMenuComponent.jsx";

const TileMenu = ({ tileData, onClose }) => {
    if (!tileData) return null;

    return (
        <div className="overlay">
            <div className="overlay-section">
                <TileMenuTitleComponent name={tileData.tiles[0].tree.name} isTitle="true"/>
                <TileMenuTitleComponent name={`Owner: ${tileData.tiles[0].tree.owner}`}/>
                <TileMenuTitleComponent name={`x: ${tileData.tiles[0].position.x}, y: ${tileData.tiles[0].position.y}`}/>
            </div>
            <div className="overlay-resource-info">
                <div className="overlay-resource">
                    <TileMenuResourceComponent valueName={"leaves"} value={tileData.tiles[0].tree.leaves}/>
                    <TileMenuResourceComponent valueName={"troops"} value={580}/>
                </div>
            </div>
            <div className="overlay-section">
                <TileMenuComponent valueName={"Leaves production"} value={tileData.tiles[0].tree.leavesProduction} buttonFunction={null} width={5} buttonText={"Upgrade"}/>
            </div>
            <div className="overlay-section">
                <button className="test-button" onClick={onClose}>
                    <WoodBox n={5} text="Close" />
                </button>
            </div>
        </div>
    );
};

export default TileMenu;