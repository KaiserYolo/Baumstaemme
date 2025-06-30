import React, { useRef, useEffect, useState } from 'react';
import TileMenu from '../components/TileMenu.jsx';
import { initializePixiApp, setupMapContainers, loadAndRenderTiles } from '../pixi/MapInitializer';
import { setupPanningAndZooming } from '../pixi/InteractionManager';

const MapView = ({ gameId }) => {
    const pixiContainerRef = useRef(null);
    const appRef = useRef(null);
    const mapContainerRef = useRef(null);
    const viewportRef = useRef(null);

    const enableInteractionRef = useRef(null);
    const disableInteractionRef = useRef(null);
    const cleanupInteractionManagerRef = useRef(null);

    const [selectedTile, setSelectedTile] = useState(null);


    useEffect(() => {
        const app = initializePixiApp(pixiContainerRef.current, window.innerWidth, window.innerHeight);
        appRef.current = app;

        const { mapContainer } = setupMapContainers(app);
        mapContainerRef.current = mapContainer;

        const initMap = async () => {
            const mapBounds = await loadAndRenderTiles(mapContainer, setSelectedTile, gameId);
            if (mapBounds) {
                const { viewport, enableInteraction, disableInteraction, cleanupInteractionManager } = setupPanningAndZooming(app, mapContainer, mapBounds); //getIsMenuOpen
                        viewportRef.current = viewport;
                if (viewport){
                    viewportRef.current = viewport;
                    enableInteractionRef.current = enableInteraction;
                    disableInteractionRef.current = disableInteraction;
                    cleanupInteractionManagerRef.current = cleanupInteractionManager;
                } else {
                    console.log("viewport didn't initialise", viewport);
                }
            }
        };

        initMap();

        return () => {
            if (cleanupInteractionManagerRef.current) {
                cleanupInteractionManagerRef.current();
                cleanupInteractionManagerRef.current = null;
            }

            if (viewportRef.current) {
                viewportRef.current.destroy({children: true, texture: true, baseTexture: true});
                viewportRef.current = null;
            }

            if (appRef.current) {
                appRef.current.destroy(true);
                appRef.current = null;
            }
        };
    }, [gameId]);

    useEffect(() => {
        if (selectedTile !== null) {
            if (enableInteractionRef.current && typeof disableInteractionRef.current === 'function') {
                disableInteractionRef.current();
            } else {
                console.warn("disableInteraction function not available.");
            }
        } else {
            if (disableInteractionRef.current && typeof enableInteractionRef.current === 'function') {
                enableInteractionRef.current();
            } else {
                console.warn("enableInteraction function not available.");
            }
        }
    }, [selectedTile]);



    useEffect(() => {
        const handleResize = () => {
            if (appRef.current) {
                appRef.current.renderer.resize(window.innerWidth, window.innerHeight);
            }
        };

        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    return (
        <div className="Map">
            {selectedTile && <TileMenu
                tileId = {{id: selectedTile.id}}
                onClose={() => setSelectedTile(null)}
            />}
            <div ref={pixiContainerRef}/>
        </div>
    );
};

export default MapView;