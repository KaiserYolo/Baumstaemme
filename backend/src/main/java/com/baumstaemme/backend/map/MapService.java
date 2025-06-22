package com.baumstaemme.backend.map;

import com.baumstaemme.backend.tile.TileUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {

    private final int TILE_SIZE = 64;

    private final TileUtil tileUtil;
    private final MapRepo mapRepo;

    MapService(TileUtil tileUtil, MapRepo mapRepo) {
        this.tileUtil = tileUtil;
        this.mapRepo = mapRepo;
    }


    Map createEmptyMap(int length, int height) {

        Map map = new Map();
        map.setLength(length);
        map.setHeight(height);

        return saveMap(map);
    }

    Map createMap(int mapWidth, int mapHeight) {
        Map map = new Map();
        map.setLength(mapWidth);
        map.setHeight(mapHeight);
        map.setTiles(tileUtil.createTiles(TILE_SIZE, TILE_SIZE,  mapWidth, mapHeight));
        return mapRepo.save(map);
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
