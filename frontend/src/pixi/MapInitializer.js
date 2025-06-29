// src/pixi/MapInitializer.js
import * as PIXI from 'pixi.js';
import {getMap} from "../services/MapAPI.js";

const TILE_SIZE = 64; // Konstante für die Kachelgröße
const MAP_ID = 1;
const TILE_ASSETS = {
    tree: "/assets/Baum.png",
    leer: "/assets/leer.png",
}

export const initializePixiApp = (containerRef, width, height) => {
    const app = new PIXI.Application({
        width: width,
        height: height,
        backgroundColor: 0x0d7527,
        antialias: true, // Optional: für glattere Kanten
    });
    containerRef.appendChild(app.view);
    return app;
};

export const setupMapContainers = (app) => {
    const mapContainer = new PIXI.Container();
    app.stage.addChild(mapContainer);

    const uiContainer = new PIXI.Container();
    app.stage.addChild(uiContainer);

    return { mapContainer, uiContainer };
};

export const loadAndRenderTiles = async (mapContainer, setSelectedTile) => {
    const backendData = await getMap(MAP_ID);

    if (!backendData || !backendData.tiles) {
        console.error("Failed to load map data or essential properties are missing.");
        return null;
    }

    await PIXI.Assets.load(Object.values(TILE_ASSETS));

    let maxX = 0;
    let maxY = 0;

    maxX = Math.max(backendData.size * TILE_SIZE, window.innerWidth * 1.5);
    maxY = Math.max(backendData.size * TILE_SIZE, window.innerHeight * 1.5);


    backendData.tiles.forEach(tileInfo => {
        const texturePath = TILE_ASSETS[tileInfo.type.toLowerCase()];
        if (!texturePath) {
            console.warn(`No texture found for tile type: ${tileInfo.type}`);
            return;
        }

        const tile = new PIXI.Sprite(PIXI.Assets.get(texturePath));

        tile.x = tileInfo.position.x*TILE_SIZE;
        tile.y = tileInfo.position.y*TILE_SIZE;

        tile.width = TILE_SIZE;
        tile.height = TILE_SIZE;

        tile.interactive = true;
        tile.buttonMode = true;
        tile.tileData = tileInfo; // Eigene Daten an das Sprite-Objekt hängen

        tile.on('pointerdown', (e) => {
            tile.initialPointerDownGlobal = e.data.global.clone();
        });


        tile.on('pointerup', (e) => {
            if (!tile.initialPointerDownGlobal) {
                console.warn("pointerup fired without a preceding pointerdown for this tile. Skipping click/drag check.");
                return;
            }
            const currentPointerUpGlobal = e.data.global.clone();
            const distance = Math.sqrt(
                Math.pow(currentPointerUpGlobal.x - tile.initialPointerDownGlobal.x, 2) +
                Math.pow(currentPointerUpGlobal.y - tile.initialPointerDownGlobal.y, 2)
            );

            const DRAG_THRESHOLD = 5;

            if (distance <= DRAG_THRESHOLD) {
                console.log(`Kachel geklickt:`, tile.tileData);
                setSelectedTile(tile.tileData);
                //e.stopPropagation();
            }else {
                console.log("Kachel gedraggt (Menü nicht geöffnet):", tile.tileData)
            }
            delete tile.initialPointerDownGlobal;
        });

        mapContainer.addChild(tile);
        if (tileInfo.position.x*TILE_SIZE > maxX) maxX = tileInfo.position.x*TILE_SIZE;
        if (tileInfo.position.y*TILE_SIZE > maxY) maxY = tileInfo.position.y*TILE_SIZE;
    });
    const mapBounds = {
        width: maxX,
        height: maxY,
    };
    return mapBounds;
};