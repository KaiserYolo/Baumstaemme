package com.baumstaemme.backend.game.tile;

import org.springframework.stereotype.Service;

@Service
public class TileService {

    TileRepo tileRepo;

    public TileService(TileRepo tileRepo) {
        this.tileRepo = tileRepo;
    }


    public Tile save(Tile tile) {
        return tileRepo.save(tile);
    }


    public Tile create(int x, int y) {
        Tile tile = new Tile();
        tile.setXCoordinate(x);
        tile.setYCoordinate(y);
        tile.setType(TileType.TREE);
        return tileRepo.save(tile);
    }


    /*public List<Tile> createTiles(int width, int height) {
        List<Tile> newTiles = new ArrayList<>();
        return newTiles;
    }*/


}
