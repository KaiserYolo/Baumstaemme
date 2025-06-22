// src/pixi/MapInitializer.js
import * as PIXI from 'pixi.js';
import {getMap} from "../services/MapAPI.js";

const TILE_SIZE = 64; // Konstante für die Kachelgröße
const MAP_ID = 15;
const TILE_ASSETS = {
    tree: "/assets/test_dorf.png",
    leer: "/assets/leer.png",
}

export const initializePixiApp = (containerRef, width, height) => {
    const app = new PIXI.Application({
        width: width,
        height: height,
        backgroundColor: 0x2c3e50,
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

    const helpText = new PIXI.Text('Klicken & Ziehen zum Bewegen, Mausrad zum Zoomen', { fontSize: 14, fill: 'white' });
    helpText.x = 10;
    helpText.y = 10;
    uiContainer.addChild(helpText);

    return { mapContainer, uiContainer };
};

export const loadAndRenderTiles = async (mapContainer, setSelectedTile) => {
    const backendData = await getMap(MAP_ID);

    if (!backendData || !backendData.tiles) {
        console.error("Failed to load map data or essential properties are missing.");
        return;
    }

    // Lade alle Texturen, die benötigt werden
    await PIXI.Assets.load(Object.values(TILE_ASSETS));

    backendData.tiles.forEach(tileInfo => {
        const texturePath = TILE_ASSETS[tileInfo.type.toLowerCase()];
        if (!texturePath) {
            console.warn(`No texture found for tile type: ${tileInfo.type}`);
            return; // Skip rendering this tile if texture path is missing
        }

        const tile = new PIXI.Sprite(PIXI.Assets.get(texturePath));

        tile.x = tileInfo.xcoordinate;
        tile.y = tileInfo.ycoordinate;

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
                return; // Exit the handler early if no initial position was recorded
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
            }else {
                console.log("Kachel gedraggt (Menü nicht geöffnet):", tile.tileData)
            }
            delete tile.initialPointerDownGlobal;
        });

        mapContainer.addChild(tile);
    });
};