// src/pixi/InteractionManager.js
import * as PIXI from 'pixi.js';

// Ändere den Parameter von `isMenuOpen` zu `getIsMenuOpen`
export const setupPanningAndZooming = (app, mapContainer, getIsMenuOpen) => {
    let isDragging = false;
    let prevPosition = null;
    let dragStartPoint = null;
    const DRAG_THRESHOLD = 5;

    app.stage.interactive = true;
    app.stage.hitArea = app.screen;

    let pointerDownHandler, pointerUpHandler, pointerUpOutsideHandler, pointerMoveHandler, wheelHandler;

    const enableInteractions = () => {
        disableInteractions();

        pointerDownHandler = (e) => {
            // Hier prüfen wir den aktuellen Zustand des Menüs
            if (getIsMenuOpen()) return; // Wenn das Menü geöffnet ist, kein Panning starten

            isDragging = true;
            prevPosition = e.data.global.clone();
            dragStartPoint = e.data.global.clone();
        };

        pointerUpHandler = () => {
            isDragging = false;
            dragStartPoint = null;
        };

        pointerUpOutsideHandler = () => {
            isDragging = false;
            prevPosition = null;
        };

        pointerMoveHandler = (e) => {
            if (isDragging && prevPosition && dragStartPoint) {
                const newPosition = e.data.global.clone();

                const distance = Math.sqrt(
                    Math.pow(newPosition.x - dragStartPoint.x, 2) +
                    Math.pow(newPosition.y - dragStartPoint.y, 2)
                );

                // Wenn die Maus sich signifikant bewegt hat UND kein Menü offen ist,
                // dann ist es ein Drag und wir bewegen die Map.
                if (distance > DRAG_THRESHOLD && !getIsMenuOpen()) { // Auch hier den aktuellen Zustand prüfen
                    const dx = newPosition.x - prevPosition.x;
                    const dy = newPosition.y - prevPosition.y;

                    mapContainer.x += dx;
                    mapContainer.y += dy;
                }
                //console.log("old " + prevPosition.x, prevPosition.y);
                //console.log("new " + newPosition.x, newPosition.y);
                prevPosition = newPosition;
            }
        };

        wheelHandler = (e) => {
            // Hier prüfen wir den aktuellen Zustand des Menüs
            if (getIsMenuOpen()) {
                e.preventDefault();
                return;
            }

            e.preventDefault();
            const zoomFactor = e.deltaY > 0 ? 0.9 : 1.1;


            const mousePoint = new PIXI.Point(e.offsetX, e.offsetY);
            const worldPoint = mapContainer.toLocal(mousePoint);

            mapContainer.scale.x *= zoomFactor;
            mapContainer.scale.y *= zoomFactor;

            const newMousePoint = mapContainer.toGlobal(worldPoint);

            mapContainer.x -= (newMousePoint.x - mousePoint.x);
            mapContainer.y -= (newMousePoint.y - mousePoint.y);
        };

        app.stage.on('pointerdown', pointerDownHandler);
        app.stage.on('pointerup', pointerUpHandler);
        app.stage.on('pointerupoutside', pointerUpOutsideHandler);
        app.stage.on('pointermove', pointerMoveHandler);
        app.view.addEventListener('wheel', wheelHandler);
    };

    const disableInteractions = () => {
        if (pointerDownHandler) app.stage.off('pointerdown', pointerDownHandler);
        if (pointerUpHandler) app.stage.off('pointerup', pointerUpHandler);
        if (pointerUpOutsideHandler) app.stage.off('pointerupoutside', pointerUpOutsideHandler);
        if (pointerMoveHandler) app.stage.off('pointermove', pointerMoveHandler);
        if (wheelHandler) app.view.removeEventListener('wheel', wheelHandler);
    };

    // Initialisiere die Interaktionen nur, wenn das Menü nicht geöffnet ist (beim ersten Rendern)
    if (!getIsMenuOpen()) {
        enableInteractions();
    }

    return { enableInteractions, disableInteractions };
};