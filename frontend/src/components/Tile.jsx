//AI generated Code

import * as PIXI from 'pixi.js';
import { Sprite} from "@pixi/react";
//import {useMemo} from "react";
import {Texture} from "pixi.js";
import dorfImg from '../assets/test_dorf.png';
import waldImg from '../assets/wald.png';
import leerImg from '../assets/leer.png';

const TILE_SIZE = 64;

const tileTextures = {
    dorf: PIXI.Texture.from(dorfImg),
    wald: PIXI.Texture.from(waldImg),
    leer: PIXI.Texture.from(leerImg),
};

// const tileImages = useMemo(() => Texture.from('/assets/.png'), []);

export default function Tile({ x, y, type }) {
    return (
        <Sprite
            texture={tileTextures[type] || tileTextures['leer']}
            x={x * TILE_SIZE}
            y={y * TILE_SIZE}
            width={TILE_SIZE}
            height={TILE_SIZE}
            interactive
            pointerdown={() => console.log(`Tile: ${type} at (${x}, ${y})`)}
        />
    );
}
