// src/pixi/InteractionManager.js
import * as PIXI from 'pixi.js';

export const setupPanningAndZooming = (app, mapContainer) => {
    let isDragging = false;
    let prevPosition = null;

    app.stage.interactive = true;
    app.stage.hitArea = app.screen; // Wichtig, damit Klicks außerhalb von Objekten auf der Stage registriert werden

    app.stage.on('pointerdown', (e) => {
        if (e.target !== app.stage) return; // Nur interagieren, wenn direkt auf die Stage geklickt wird
        isDragging = true;
        prevPosition = e.data.global.clone();
    });

    app.stage.on('pointerup', () => {
        isDragging = false;
        prevPosition = null;
    });

    app.stage.on('pointerupoutside', () => {
        isDragging = false;
        prevPosition = null;
    });

    app.stage.on('pointermove', (e) => {
        if (isDragging && prevPosition) {
            const newPosition = e.data.global.clone();
            const dx = newPosition.x - prevPosition.x;
            const dy = newPosition.y - prevPosition.y;

            mapContainer.x += dx;
            mapContainer.y += dy;

            prevPosition = newPosition;
        }
    });

    app.view.addEventListener('wheel', (e) => {
        e.preventDefault();
        const zoomFactor = e.deltaY > 0 ? 0.9 : 1.1; // Zoom out/in

        const mousePoint = new PIXI.Point(e.offsetX, e.offsetY);
        const worldPoint = mapContainer.toLocal(mousePoint);

        mapContainer.scale.x *= zoomFactor;
        mapContainer.scale.y *= zoomFactor;

        const newMousePoint = mapContainer.toGlobal(worldPoint);

        mapContainer.x -= (newMousePoint.x - mousePoint.x);
        mapContainer.y -= (newMousePoint.y - mousePoint.y);
    });
};