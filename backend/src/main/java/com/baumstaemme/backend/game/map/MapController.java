package com.baumstaemme.backend.game.map;


import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maps")
public class MapController {

    private final MapService mapService;

    private static final String PLAYER_SESSION_ID_KEY = "playerSession";

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<MapDto> getMapById(@PathVariable Long id, HttpSession session) {
        Long playerId = (Long) session.getAttribute(PLAYER_SESSION_ID_KEY); // TODO ich brauche die ID unbedingt oder ich komme
        if (playerId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //Long playerId = 1L; // Placeholder
        Map map = mapService.findById(id);
        if (map == null) {
            return null;
        }
        MapDto responseDto = MapUtil.createResponseDto(map, playerId);
        return ResponseEntity.ok(responseDto);
    }
}
