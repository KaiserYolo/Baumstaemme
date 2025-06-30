package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.player.Player;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private static final String USER_SESSION_KEY = "loggedInUser";
    private static final String PLAYER_SESSION_ID_KEY = "playerSession";

    private final GameService gameService;

    private static final Logger log = LoggerFactory.getLogger(GameController.class); // Maybe rausnehmen

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public ResponseEntity<List<GameDto>> getAll() {
        List<Game> games = gameService.findAll();
        List<GameDto> gameDtoList = GameUtil.createResponseDto(games);
        return ResponseEntity.ok(gameDtoList);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> findById(@PathVariable Long gameId) {
        Game game = gameService.findById(gameId);
        GameDto responseDto = GameUtil.createResponseDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<GameDto> create(@Valid @RequestBody GameDto gameDto) {
        Game game = gameService.create(gameDto.getName(), gameDto.getMapSize());
        GameDto responseDto = GameUtil.createResponseDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{gameId}/updateStatus")
    public ResponseEntity<GameDto> updateStatus(@PathVariable Long gameId, @Valid @RequestBody GameDto gameDto) {
        Game game = gameService.updateStatus(gameId, gameDto.getStatus());
        GameDto responseDto = GameUtil.createResponseDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{gameId}/join")
    public ResponseEntity<String> joinGame(@PathVariable Long gameId, HttpSession session) {
        log.info("Attempting to join game {}. Session ID: {}", gameId, session.getId());

        Long userId = (Long) session.getAttribute(USER_SESSION_KEY);

        if (userId == null) {
            log.warn("No user found in session for game join attempt.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        Player player = gameService.joinGame(gameId, userId);

        session.setAttribute(PLAYER_SESSION_ID_KEY, player.getId());
        log.info("User {} successfully joined game {} as player {}.", userId, gameId, player.getId());

        return ResponseEntity.ok().body("Sucessfully joined game.");
    }
}
