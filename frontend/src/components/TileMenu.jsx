import React from 'react';
import WoodBox from "./WoodBox.jsx";
import '../App.css';
import {TileMenuComponent, TileMenuResourceComponent, TileMenuTitleComponent} from "./TileMenuComponent.jsx";
import {getTree} from "../services/TreeAPI.js";

const TileMenu = ({ tileId, onClose }) => {
    let id = tileId.id
    let treeData
    try{
        treeData  =  getTree(id);
        console.log(treeData);
        // eslint-disable-next-line no-unused-vars
    }catch (error) {
        treeData = null;
    }
    console.log(treeData);
    if (!treeData){
        treeData = {
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
        //throw new Error("No tile data found");
    }

    return (
        <div className="overlay">
            <div className="overlay-section">
                {/* <TileMenuTitleComponent name={items.tiles[0].tree.name} isTitle="true"/> */}
                <TileMenuTitleComponent name={`Owner: ${treeData.tiles[0].tree.owner}`}/>
                <TileMenuTitleComponent name={`x: ${treeData.tiles[0].position.x}, y: ${treeData.tiles[0].position.y}`}/>
            </div>
            <div className="overlay-resource-info">
                <div className="overlay-resource">
                    <TileMenuResourceComponent valueName={"leaves"} value={treeData.tiles[0].tree.leaves}/>
                    <TileMenuResourceComponent valueName={"troops"} value={580}/>
                </div>
            </div>
            <div className="overlay-section">
                <TileMenuComponent valueName={"Leaves production"} value={items.tiles[0].tree.leavesProduction} buttonFunction={null} width={5} buttonText={"Upgrade"}/>
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