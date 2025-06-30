import React, {useEffect, useState} from 'react';
import WoodBox from "./WoodBox.jsx";
import '../App.css';
import {TileMenuComponent, TileMenuResourceComponent, TileMenuTitleComponent} from "./TileMenuComponent.jsx";
import {getTree} from "../services/TreeAPI.js";

const TileMenu = ({ tileId, onClose }) => {
    console.log("TileMenu rendering, tileId:", tileId); // Add this line
    const [treeData, setTreeData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTreeData = async () => {
            setLoading(true);
            setError(null);
            try {
                const data = await getTree(tileId.id);
                console.log(data);
                setTreeData(data);
            } catch (err) {
                console.error("Failed to fetch tree data:", err);
                setError(err);
            } finally {
                setLoading(false);
            }
        };

            fetchTreeData();
    }, [tileId]);

    if (loading) {
        return <div className="overlay">Loading tree data...</div>;
    }

    if (error && !treeData) {
        return <div className="overlay">Error loading tree data. Please try again.</div>;
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