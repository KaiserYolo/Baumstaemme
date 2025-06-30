package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.user.User;
import com.baumstaemme.backend.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    private static final String USER_SESSION_KEY = "loggedInUser";
    private static final String PLAYER_SESSION_ID_KEY = "playerSession";
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
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
        System.out.println(session.getAttribute(USER_SESSION_KEY));
        System.out.println(session);
        Long userId = (Long) session.getAttribute(USER_SESSION_KEY);
        if (gameService.findById(id) == null && userService.findById(userId) == null) {
            return ResponseEntity.badRequest().build();
        }
        Player player = gameService.joinGame(id, userId);
        session.setAttribute(PLAYER_SESSION_ID_KEY, player.getId());
        return ResponseEntity.ok("Server gejoint");
    }
}
