package com.baumstaemme.backend.tile;

import com.baumstaemme.backend.map.Map;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TileUtil {


    private final TileRepo tileRepo;

    public TileUtil(TileRepo tileRepo) {
        this.tileRepo = tileRepo;
    }

    public Tile createTile(int xCoordinate, int yCoordinate, TileType type) {
        Tile tile = new Tile(xCoordinate, yCoordinate, type);
        tileRepo.save(tile);
        return tile;
    }


    public List<Tile> createTiles(int width, int height, int mapWidth, int mapHeight) {
        List<Tile> newTiles = new ArrayList<>();
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                newTiles.add(createTile(height*y, width*x, TileType.TREE));  //TODO: type muss gemacht werden
            }
        }
        return newTiles;
    }


}
