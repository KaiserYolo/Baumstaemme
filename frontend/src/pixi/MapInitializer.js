// src/pixi/MapInitializer.js
import * as PIXI from 'pixi.js';
import { fetchMapData } from '../utils/ApiMapTest.js';

const TILE_SIZE = 64; // Konstante für die Kachelgröße

export const initializePixiApp = (containerRef) => {
    const app = new PIXI.Application({
        width: 800,
        height: 600,
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
    const backendData = await fetchMapData();

    // Lade alle Texturen, die benötigt werden
    await PIXI.Assets.load(Object.values(backendData.assets));

    backendData.tiles.forEach(tileInfo => {
        const texturePath = backendData.assets[tileInfo.type.toLowerCase()];
        const tile = new PIXI.Sprite(PIXI.Assets.get(texturePath));
        tile.x = tileInfo.x * TILE_SIZE;
        tile.y = tileInfo.y * TILE_SIZE;
        tile.width = TILE_SIZE;
        tile.height = TILE_SIZE;

        tile.interactive = true;
        tile.buttonMode = true;
        tile.tileData = tileInfo; // Eigene Daten an das Sprite-Objekt hängen

        tile.on('pointerdown', (e) => {
            e.stopPropagation(); // Verhindert, dass das Panning ausgelöst wird
            console.log(`Kachel geklickt:`, tile.tileData);
            setSelectedTile(tile.tileData);
        });

        mapContainer.addChild(tile);
    });
};