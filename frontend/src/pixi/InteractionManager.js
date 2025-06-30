import { Viewport} from "pixi-viewport";

export const setupPanningAndZooming = (app, mapContainer, mapBounds) => {

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
        .pinch();

    viewport.clamp({
        direction: "all",
        overflow: "bounce"
    });

    viewport.clampZoom({
        minScale: 0.5,
        maxScale: 1.5,
    });

    const viewportResizeHandler = () => {
        viewport.resize(window.innerWidth, window.innerHeight);
    };
    window.addEventListener('resize', viewportResizeHandler);


    const enableInteraction = () => {
        viewport.plugins.resume('drag');
        viewport.plugins.resume('wheel');
        viewport.plugins.resume('pinch');
    };

    const disableInteraction = () => {
        viewport.plugins.pause('drag');
        viewport.plugins.pause('wheel');
        viewport.plugins.pause('pinch');
    }

    const cleanupInteractionManager = () => {
        window.removeEventListener('resize', viewportResizeHandler);
        // You might also want to destroy the viewport here if it's not destroyed elsewhere
        // viewport.destroy({ children: true, texture: true, baseTexture: true });
    };

    return { viewport, enableInteraction, disableInteraction, cleanupInteractionManager };
};