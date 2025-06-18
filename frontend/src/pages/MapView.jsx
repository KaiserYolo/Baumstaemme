// src/components/MapView.jsx
import React, { useRef, useEffect, useState, useCallback } from 'react'; // Füge useCallback hinzu
import TileMenu from '../components/TileMenu.jsx';
import { initializePixiApp, setupMapContainers, loadAndRenderTiles } from '../pixi/MapInitializer';
import { setupPanningAndZooming } from '../pixi/InteractionManager';

const MapView = () => {
    const pixiContainerRef = useRef(null);
    const appRef = useRef(null);
    const mapContainerRef = useRef(null);
    const interactionManagerRef = useRef(null);

    const [selectedTile, setSelectedTile] = useState(null);

    // Erstelle eine Callback-Funktion, die den aktuellen Zustand von selectedTile zurückgibt
    // Dies verhindert, dass setupPanningAndZooming neu initialisiert werden muss
    const getIsMenuOpen = useCallback(() => {
        return selectedTile !== null;
    }, [selectedTile]); // selectedTile als Abhängigkeit, damit der Callback aktualisiert wird

    useEffect(() => {
        const app = initializePixiApp(pixiContainerRef.current, window.innerWidth, window.innerHeight);
        appRef.current = app;

        const { mapContainer } = setupMapContainers(app);
        mapContainerRef.current = mapContainer;

        // Übergib die getIsMenuOpen Funktion an setupPanningAndZooming
        interactionManagerRef.current = setupPanningAndZooming(app, mapContainer, getIsMenuOpen);

        loadAndRenderTiles(mapContainer, setSelectedTile);

        return () => {
            if (appRef.current) {
                appRef.current.destroy(true, true);
                appRef.current = null;
            }
        };
    }, []);

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