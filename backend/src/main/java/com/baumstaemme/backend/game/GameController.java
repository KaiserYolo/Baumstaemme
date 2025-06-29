package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.player.PlayerService;
import com.baumstaemme.backend.user.User;
import com.baumstaemme.backend.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    private static final String USER_SESSION_KEY = "loggedInUser";
    private static final String PLAYER_SESSION_ID_KEY = "playerSession";

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public ResponseEntity<List<GameDto>> getAll() {
        List<Game> games = gameService.getAll();
        List<GameDto> gameDtoList = GameUtil.createResponseDto(games);
        return ResponseEntity.ok(gameDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> findById(@PathVariable Long id) {
        Game game = gameService.findById(id);
        if (game == null) {
            return ResponseEntity.ok(null);
        }
        GameDto responseDto = GameUtil.createResponseDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<GameDto> create(@Valid @RequestBody GameDto gameDto) {
        Game game = gameService.create(gameDto);
        GameDto responseDto = GameUtil.createResponseDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<GameDto> updateStatus(@Valid @RequestBody GameDto gameDto) {
        Game game = gameService.updateStatus(gameDto.getId(), gameDto.getStatus());
        GameDto responseDto = GameUtil.createResponseDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinGame(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute(USER_SESSION_KEY);

        if (gameService.findById(id) == null && user == null) {
            return ResponseEntity.badRequest().build();
        }
        Player player = gameService.joinGame(id, user.getId());
        session.setAttribute(PLAYER_SESSION_ID_KEY, player.getId());
        return ResponseEntity.ok("Server gejoint");
    }
}
