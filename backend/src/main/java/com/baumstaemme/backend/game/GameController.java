package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.map.Map;
import com.baumstaemme.backend.game.map.MapConverter;
import com.baumstaemme.backend.game.map.MapDto;
import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.player.PlayerConverter;
import com.baumstaemme.backend.game.player.PlayerDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getAllIds() {
        List<Long> ids = gameService.getAllIds();
        return ResponseEntity.ok(ids);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> findById(@PathVariable Long id) {
        Game game = gameService.findById(id);
        if (game == null) {
            return ResponseEntity.ok(null);
        }
        GameDto responseDto = GameConverter.toDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<PlayerDto>> getPlayers(@PathVariable Long id) {
        if (gameService.findById(id) == null) {
            return ResponseEntity.ok(null);
        }
        List<Player> players = gameService.getPlayers(id);
        List<PlayerDto> responseDto = PlayerConverter.toDtoList(players);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/map")
    public ResponseEntity<MapDto> getMap(@PathVariable Long id) {
        if (gameService.findById(id) == null) {
            return ResponseEntity.ok(null);
        }
        Map map = gameService.getMap(id);
        if (map == null) {
            return ResponseEntity.ok(null);
        }
        MapDto responseDto = MapConverter.toDto(map);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<GameDto> create(@Valid @RequestBody GameDto gameDto) {
        Game game = gameService.create(gameDto);
        GameDto responseDto = GameConverter.toDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<GameDto> updateStatus(@Valid @RequestBody GameDto gameDto) {
        Game game = gameService.updateStatus(gameDto.getId(), gameDto.getStatus());
        GameDto responseDto = GameConverter.toDto(game);
        return ResponseEntity.ok(responseDto);
    }
}
