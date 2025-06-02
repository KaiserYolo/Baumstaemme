package com.baumstaemme.backend.map;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {

    private final MapRepo mapRepo;

    MapService(MapRepo mapRepo) {
        this.mapRepo = mapRepo;
    }


    Map createEmptyMap(int length, int height) {

        Map map = new Map();
        map.setLength(length);
        map.setHeight(height);

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
