// src/components/MapView.jsx
import React, { useRef, useEffect, useState } from 'react';
import TileMenu from '../components/TileMenu.jsx';
import { initializePixiApp, setupMapContainers, loadAndRenderTiles } from '../pixi/MapInitializer';
import { setupPanningAndZooming } from '../pixi/InteractionManager';

const MapView = () => {
    const pixiContainerRef = useRef(null);
    const appRef = useRef(null);
    const mapContainerRef = useRef(null);

    const [selectedTile, setSelectedTile] = useState(null);

    useEffect(() => {
        const app = initializePixiApp(pixiContainerRef.current);
        appRef.current = app;

        const { mapContainer } = setupMapContainers(app);
        mapContainerRef.current = mapContainer;

        loadAndRenderTiles(mapContainer, setSelectedTile);
        setupPanningAndZooming(app, mapContainer);

        // Cleanup-Funktion
        return () => {
            if (appRef.current) {
                appRef.current.destroy(true, true);
                appRef.current = null;
            }
        };
    }, []);

    return (
        <div className="map-wrapper">
            <TileMenu
                tileData={selectedTile}
                onClose={() => setSelectedTile(null)}
            />
            <div ref={pixiContainerRef} style={{ width: '800px', height: '600px' }} />
        </div>
    );
};

export default MapView;