// src/pixi/InteractionManager.js
import * as PIXI from 'pixi.js';

// Ändere den Parameter von `isMenuOpen` zu `getIsMenuOpen`
export const setupPanningAndZooming = (app, mapContainer, getIsMenuOpen, mapBounds) => {
    let isDragging = false;
    let dragStartPoint = null;
    let mapStartPoint = null;


    app.stage.interactive = true;
    app.stage.hitArea = app.screen;

    let pointerDownHandler, pointerUpHandler, pointerUpOutsideHandler, pointerMoveHandler, wheelHandler;

    const enableInteractions = () => {
        disableInteractions();

        pointerDownHandler = (e) => {
            if (getIsMenuOpen()) return; // Wenn das Menü geöffnet ist, kein Panning starten

            isDragging = true;
            dragStartPoint = e.data.global.clone();
            mapStartPoint = { x: mapContainer.x, y: mapContainer.y };
            app.view.style.cursor = 'grabbing';
        };

        pointerUpHandler = () => {
            isDragging = false;
            dragStartPoint = null;
            mapStartPoint = null;
            app.view.style.cursor = 'grab';
        };

        pointerUpOutsideHandler = () => {
            isDragging = false;
            dragStartPoint = null;
            mapStartPoint = null;
        };

        pointerMoveHandler = (e) => {
            if (isDragging && dragStartPoint && mapStartPoint) {
                if (getIsMenuOpen()) return; // Prüfe, ob das Menü während des Drags geöffnet wurde

                const newPosition = e.data.global;

                // Berechne den gesamten Offset vom Startpunkt des Drags
                const offsetX = newPosition.x - dragStartPoint.x;
                const offsetY = newPosition.y - dragStartPoint.y;

                let newX = mapStartPoint.x + offsetX;
                let newY = mapStartPoint.y + offsetY;

                // NEU: Logik zur Begrenzung der Position
                const screenWidth = app.screen.width;
                const screenHeight = app.screen.height;
                const mapWidth = mapBounds.width * mapContainer.scale.x;
                const mapHeight = mapBounds.height * mapContainer.scale.y;

                // Begrenze die linke/obere Kante
                if (newX > 0) newX = 0;
                if (newY > 0) newY = 0;

                // Begrenze die rechte/untere Kante
                if (newX < screenWidth - mapWidth) newX = screenWidth - mapWidth;
                if (newY < screenHeight - mapHeight) newY = screenHeight - mapHeight;

                mapContainer.x = newX;
                mapContainer.y = newY;
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