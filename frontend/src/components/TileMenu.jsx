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
            "id": 17,
            "name": "Baum",
            "position": {
                "x": 65.0,
                "y": 92.0
            },
            "ownerId": null,
            "ownerName": null,
            "leaves": 0,
            "leafProduction": 0,
            "trunk": 0,
            "bark": 0,
            "branches": 0,
            "root": 0,
            "upgrade":null
        }
        //throw new Error("No tile data found");
    }

    return (
        <div className="overlay">
            <div className="overlay-section">
                <TileMenuTitleComponent name={treeData.name} isTitle="true"/>
                <TileMenuTitleComponent name={`Owner: ${treeData.ownerName}`}/>
                <TileMenuTitleComponent name={`x: ${treeData.position.x}, y: ${treeData.position.y}`}/>
            </div>
            <div className="overlay-resource-info">
                <div className="overlay-resource">
                    <TileMenuResourceComponent valueName={"leaves"} value={treeData.leaves}/>
                    <TileMenuResourceComponent valueName={"troops"} value={580}/>
                </div>
            </div>
            <div className="overlay-section">
                <TileMenuComponent valueName={"Trunk"} value={treeData.trunk} buttonFunction={null} width={5} buttonText={"Upgrade"}/>
                <TileMenuComponent valueName={"Bark"} value={treeData.bark} buttonFunction={null} width={5} buttonText={"Upgrade"}/>
                <TileMenuComponent valueName={"Branches"} value={treeData.branches} buttonFunction={null} width={5} buttonText={"Upgrade"}/>
                <TileMenuComponent valueName={"Root"} value={treeData.root} buttonFunction={null} width={5} buttonText={"Upgrade"}/>
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