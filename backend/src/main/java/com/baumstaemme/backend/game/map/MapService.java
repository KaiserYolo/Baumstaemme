package com.baumstaemme.backend.game.map;

import com.baumstaemme.backend.game.tile.Tile;
import com.baumstaemme.backend.game.tile.TileService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class MapService {

    private final MapRepo mapRepo;
    private final TileService tileService;

    MapService(MapRepo mapRepo, TileService tileService) {
        this.mapRepo = mapRepo;
        this.tileService = tileService;
    }

    public Map createMap(int mapSize) {
        Map map = new Map();
        map.setSize(mapSize);

        int maxTrees = 20;

        Random random = new Random();
        HashSet<Point> coords = new HashSet<>();

        while (coords.size() < maxTrees) {
            int x = random.nextInt(map.getSize());
            int y = random.nextInt(map.getSize());

            Point cord = new Point(x, y);
            coords.add(cord);
        }

        List<Tile> tiles = new ArrayList<>();

        for (Point coord : coords) {
            Tile tile = tileService.create(coord);
            tiles.add(tile);
        }
        map.setTiles(tiles);

        return saveMap(map);
    }

    Map getById(long id) {
        return mapRepo.findById(id).get();
    }

    Map saveMap(Map map) {
        return mapRepo.save(map);
    }
}
