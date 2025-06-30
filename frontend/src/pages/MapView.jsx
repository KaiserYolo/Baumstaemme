// src/components/MapView.jsx
import React, { useRef, useEffect, useState, useCallback } from 'react'; // Füge useCallback hinzu
import TileMenu from '../components/TileMenu.jsx';
import { initializePixiApp, setupMapContainers, loadAndRenderTiles } from '../pixi/MapInitializer';
import { setupPanningAndZooming } from '../pixi/InteractionManager';

const MapView = () => {
    const pixiContainerRef = useRef(null);
    const appRef = useRef(null);
    const mapContainerRef = useRef(null);
    const viewportRef = useRef(null);

    const [selectedTile, setSelectedTile] = useState(null);


    useEffect(() => {
        const app = initializePixiApp(pixiContainerRef.current, window.innerWidth, window.innerHeight);
        appRef.current = app;

        const { mapContainer } = setupMapContainers(app);
        mapContainerRef.current = mapContainer;

        const initMap = async () => {
            const mapBounds = await loadAndRenderTiles(mapContainer, setSelectedTile);
            if (mapBounds) {
                const { viewport } = setupPanningAndZooming(app, mapContainer, mapBounds); //getIsMenuOpen
                        viewportRef.current = viewport;
                if (viewport){
                    viewportRef.current = viewport;
                }
                else {
                    console.log("Viewport net richtig", viewport);
                }
            }
        };

        initMap();

        return () => {
            if (viewportRef.current) {
                viewportRef.current.destroy({children: true, texture: true, baseTexture: true});
                viewportRef.current = null;
            }

            if (appRef.current) {
                appRef.current.destroy(true);
                appRef.current = null;
            }
        };
    }, []);

    /*
    useEffect(() => {
        if (viewportRef.current) {
            if (selectedTile !== null) {
                viewportRef.current.pause();
            } else {
                viewportRef.current.resume();
            }
        }
    }, [selectedTile]);

     */


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