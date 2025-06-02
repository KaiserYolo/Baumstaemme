package com.baumstaemme.backend.tile;

import com.baumstaemme.backend.map.Map;

import java.util.ArrayList;
import java.util.List;

public class TileUtil {


    public Tile createTile(int x, int y, TileType type) {
        return new Tile(x, y, type);
    }


    /*public List<Tile> createTiles(int width, int height) {
        List<Tile> newTiles = new ArrayList<>();
        return newTiles;
    }*/


}
