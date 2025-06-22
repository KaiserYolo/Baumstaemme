package com.baumstaemme.backend.game;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/getAllIds")
    public ResponseEntity<?> getAllIds() {
        List<Long> ids = gameService.getAllIds();
        if (ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Keine Games gefunden.");
        }
        return ResponseEntity.ok(ids);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Game game = gameService.findById(id);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kein Game mit der Id: " + id + " gefunden.");
        }
        return ResponseEntity.ok(gameService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody GameDto gameDto) {
        return ResponseEntity.ok(gameService.create(gameDto));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody GameDto gameDto) {
        Game game = gameService.findById(gameDto.getId());
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kein Game mit der Id: " + gameDto.getId() + " gefunden.");
        }
        return ResponseEntity.ok(gameService.updateStatus(gameDto.getId(), gameDto.getStatus()));
    }

}
