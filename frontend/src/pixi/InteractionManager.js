// src/pixi/InteractionManager.js
import * as PIXI from 'pixi.js';
import { Viewport} from "pixi-viewport";

export const setupPanningAndZooming = (app, mapContainer, mapBounds) => {   //getIsMenuOpen

    console.log("setupPanningAndZooming: app object", app);
    console.log("setupPanningAndZooming: app.screen", app.screen);
    console.log("setupPanningAndZooming: app.renderer", app.renderer);
    console.log("setupPanningAndZooming: app.renderer.events", app.renderer?.events); // Sicherer Zugriff

    if (!app || !app.screen || !app.renderer || !app.renderer.events || !mapBounds || typeof mapBounds.width === 'undefined' || typeof mapBounds.height === 'undefined') {
        console.error("Ungültiges App-Objekt oder Map-Grenzen an setupPanningAndZooming übergeben. Viewport kann nicht initialisiert werden.");
        return null; // Null zurückgeben, um Abstürze zu vermeiden
    }

    const viewport = new Viewport({
       worldWidth: mapBounds.width,
       worldHeight: mapBounds.height,
       screenWidth: app.screen.width,
       screenHeight: app.screen.height,
       events: app.renderer.events,
       ticker: app.ticker,
    });

    app.stage.addChild(viewport);
    viewport.addChild(mapContainer);
    viewport.moveCenter(viewport.worldWidth/2, viewport.worldHeight/2);

    viewport
        .drag()
        .wheel()
        .decelerate()
        //.pinch() mobile??

    viewport.clamp({
        direction: "all",
        overflow: "bounce" // oder discard
    });

    viewport.clampZoom({
        minScale: 0.5,
        maxScale: 1.5,
    });

    window.addEventListener('resize', () => {
        viewport.resize(window.innerWidth, window.innerHeight);
    });

/*
    const enableInteraction = () => {
        viewport.resume();
    };

    const disableInteraction = () => {
        viewport.pause();
    }

    if (getIsMenuOpen()) {
        viewport.pause();
    } else {
        viewport.resume();
    }

 */

    return { viewport };  //, enableInteraction,disableInteraction
};