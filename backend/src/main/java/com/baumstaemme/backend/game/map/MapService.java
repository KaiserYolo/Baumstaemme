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


    Map createEmptyMap(int length, int height) {

        Map map = new Map();
        //map.setLength(length);
        //map.setHeight(height);

        return saveMap(map);
    }

    public Map createMap(int mapSize) {
        Map map = new Map();
        map.setSize(mapSize);

        int maxTrees = 30;

        Random random = new Random();
        HashSet<Point> points = new HashSet<>();

        while (points.size() < maxTrees) {
            int x = random.nextInt(map.getSize());
            int y = random.nextInt(map.getSize());

            Point point = new Point(x, y);
            points.add(point);
        }

        List<Tile> tiles = new ArrayList<>();

        for (Point point : points) {
            Tile tile = tileService.create(point.x, point.y);
            tiles.add(tile);
        }
        map.setTiles(tiles);

        return saveMap(map);
    }


    List<Long> getAllMaps() {
        return mapRepo.findAllMapIds();
    }

    Map getMapsById(long id) {
        return mapRepo.findById(id).get();
    }

    Map saveMap(Map map) {
        return mapRepo.save(map);
    }
}
