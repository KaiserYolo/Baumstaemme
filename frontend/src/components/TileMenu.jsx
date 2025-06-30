import React, {useEffect, useState} from 'react';
import WoodBox from "./WoodBox.jsx";
import '../App.css';
import {TileMenuComponent, TileMenuResourceComponent, TileMenuTitleComponent} from "./TileMenuComponent.jsx";
import {getTree} from "../services/TreeAPI.js";
import {upgrade} from "../services/UpgradeAPI.js";

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

    const upgradeTree = async (id, building) => {
        try{
            const treeObj = await upgrade(id, building);
            console.log(treeObj);
        }catch(err){
            console.error("Failed to upgrade tree:", err);
        }
    }

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
                {(treeData.ownerName === null) ? (
                        <TileMenuTitleComponent name={`unclaimed`}/>
                    )
                :
                    (
                        <TileMenuTitleComponent name={`Owner: ${treeData.ownerName}`}/>
                    )}

                <TileMenuTitleComponent name={`x: ${treeData.position.x}, y: ${treeData.position.y}`}/>
            </div>
            <div className="overlay-resource-info">
                {treeData.dtoType === "PRIVATE" && <div className="overlay-resource">
                    <TileMenuResourceComponent valueName={"leaves"} value={treeData.leaves}/>
                    <TileMenuResourceComponent valueName={"troops"} value={"soon..."}/>
                </div>}

            </div>
            <div className="overlay-upgrade-info">
                {treeData.dtoType === "PRIVATE" && treeData.upgrade && treeData.upgrade.building !== null &&<div className="overlay-upgrade">
                    <TileMenuResourceComponent valueName={"upgrade"} value={treeData.upgrade.building}/>
                    <TileMenuResourceComponent valueName={"finished at"} value={new Date(treeData.upgrade.endTime).toLocaleString()}/>
                </div>}

            </div>
            <div className="overlay-section">
                {treeData.dtoType === "PRIVATE" && <TileMenuComponent valueName={"Trunk"} value={treeData.trunk} buttonFunction={() =>upgradeTree(treeData.id, treeData.upgradeInfo[0].building)} width={8} buttonText={`Upgrade: ${treeData.upgradeInfo[0].cost}`}/>}
                {treeData.dtoType === "PRIVATE" && <TileMenuComponent valueName={"Bark"} value={treeData.bark} buttonFunction={() =>upgradeTree(treeData.id, treeData.upgradeInfo[1].building)} width={8} buttonText={`Upgrade: ${treeData.upgradeInfo[1].cost}`}/>}
                {treeData.dtoType === "PRIVATE" && <TileMenuComponent valueName={"Branches"} value={treeData.branches} buttonFunction={() =>upgradeTree(treeData.id, treeData.upgradeInfo[2].building)} width={8} buttonText={`Upgrade: ${treeData.upgradeInfo[2].cost}`}/>}
                {treeData.dtoType === "PRIVATE" && <TileMenuComponent valueName={"Root"} value={treeData.root} buttonFunction={() =>upgradeTree(treeData.id, treeData.upgradeInfo[3].building)} width={8} buttonText={`Upgrade: ${treeData.upgradeInfo[3].cost}`}/>}
                {treeData.dtoType === "PUBLIC" && <TileMenuComponent valueName={"Enemy troops"} value={0} buttonFunction={null} width={5} buttonText={`Attack`}/>}
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