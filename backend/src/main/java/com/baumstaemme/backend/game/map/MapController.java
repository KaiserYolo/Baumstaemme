package com.baumstaemme.backend.game.map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/getMaps")
    public ResponseEntity<List<Long>> getAllMaps() {
        return ResponseEntity.ok(mapService.getAllMaps());
    }

    @GetMapping("/getMap")
    public ResponseEntity<Map> getMapById(@RequestParam Long id) {
        return ResponseEntity.ok(mapService.getMapsById(id));
    }

    @GetMapping("/createEmtpyMap")
    public ResponseEntity<Map> createMap(@RequestParam int length, @RequestParam int height) {

        return ResponseEntity.ok(mapService.createEmptyMap(length, height));
    }

    // Kinda useless (only kinda)
    @PostMapping
    public ResponseEntity<Map> createMap(@RequestBody Map map) {
        return ResponseEntity.ok(mapService.saveMap(map));
    }
}
