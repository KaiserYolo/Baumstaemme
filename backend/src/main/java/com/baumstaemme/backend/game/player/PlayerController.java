package com.baumstaemme.backend.game.player;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> findById(@PathVariable Long id) {
        Player player = playerService.findById(id);
        PlayerDto responseDto = PlayerUtil.createResponseDto(player);
        return ResponseEntity.ok(responseDto);
    }
}
