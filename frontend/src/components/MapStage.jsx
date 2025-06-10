import { Stage, Container } from '@pixi/react';
import {useState, useEffect, useCallback} from 'react';
import MapGrid from './MapGrid';
import {calculateCanvasSize} from "./helpers/common.js";

const MapStage = () => {

    const [zoom, setZoom] = useState(1);
    const [position, setPosition] = useState({ x: 0, y: 0 });
    const [canvasSize, setCanvasSize] = useState(() => calculateCanvasSize());

    const handleWheel = (e) => {
        e.preventDefault();
        setZoom((prev) => Math.max(0.5, Math.min(2, prev - e.deltaY * 0.001)));
    };

    const handleKeyDown = (e) => {
        const move = 50;
        if (e.key === 'ArrowUp') setPosition((p) => ({ ...p, y: p.y + move }));
        if (e.key === 'ArrowDown') setPosition((p) => ({ ...p, y: p.y - move }));
        if (e.key === 'ArrowLeft') setPosition((p) => ({ ...p, x: p.x + move }));
        if (e.key === 'ArrowRight') setPosition((p) => ({ ...p, x: p.x - move }));
    };

    const updateCanvasSize = useCallback(() => {
        setCanvasSize(calculateCanvasSize)
    },[])

    useEffect(() => {
        window.addEventListener('wheel', handleWheel, {passive: false});
        window.addEventListener('keydown', handleKeyDown);
        window.addEventListener('resize', updateCanvasSize);
        return () => {
            window.removeEventListener('wheel', handleWheel);
            window.removeEventListener('keydown', handleKeyDown);
            window.removeEventListener('resize', updateCanvasSize);
        };
    }, [updateCanvasSize]);

    return (
        <Stage width={canvasSize.width} height={canvasSize.height} options={{ backgroundColor: 0x222222 }}>
            <Container scale={zoom} x={position.x} y={position.y}>
                <MapGrid />
            </Container>
        </Stage>
    );
};

export default MapStage;
