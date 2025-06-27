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

    /*
    const getIsMenuOpen = useCallback(() => {
        return selectedTile !== null;
    }, [selectedTile]); // selectedTile als Abhängigkeit, damit der Callback aktualisiert wird

     */

    useEffect(() => {
        console.log("Breite Höhe" + window.innerWidth, window.innerHeight);
        const app = initializePixiApp(pixiContainerRef.current, window.innerWidth, window.innerHeight);
        appRef.current = app;
        console.log("APP" + app);

        const { mapContainer } = setupMapContainers(app);
        mapContainerRef.current = mapContainer;

        const initMap = async () => {
            const mapBounds = await loadAndRenderTiles(mapContainer, setSelectedTile);
            if (mapBounds) {
                const { viewport } = setupPanningAndZooming(app, mapContainer, mapBounds);
                        viewportRef.current = viewport;
            }
        };

        initMap();

        return () => {
            if (appRef.current) {
                appRef.current.destroy(true, true);
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
        <div className="map-wrapper">
            <TileMenu
                tileData={selectedTile}
                onClose={() => setSelectedTile(null)}
            />
            <div ref={pixiContainerRef}/>
        </div>
    );
};

export default MapView;