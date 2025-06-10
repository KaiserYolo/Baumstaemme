//AI generated Code

import Tile from './Tile';

const mapData = Array.from({ length: 20 }, (_, y) =>
    Array.from({ length: 30 }, (_, x) => ({
        x,
        y,
        type: (x + y) % 7 === 0 ? 'dorf' : 'wald',
    }))
);

export default function MapGrid() {
    return (
        <>
            {mapData.flat().map((tile, i) => (
                <Tile key={i} x={tile.x} y={tile.y} type={tile.type} />
            ))}
        </>
    );
}
