package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.player.PlayerService;
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
    private final PlayerService playerService;
    private final UserService userService;

    private static final String USER_SESSION_KEY = "loggedInUser";
    private static final String PLAYER_SESSION_ID_KEY = "playerSession";

    public GameController(GameService gameService, PlayerService playerService, UserService userService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<GameDto>> getAll() {
        //List<Game> gameList = gameService.getAllIds();
        return ResponseEntity.ok(null); // TODO
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

    @PutMapping("/updateStatus")
    public ResponseEntity<GameDto> updateStatus(@Valid @RequestBody GameDto gameDto) {
        Game game = gameService.updateStatus(gameDto.getId(), gameDto.getStatus());
        GameDto responseDto = GameUtil.createResponseDto(game);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinGame(@PathVariable Long id, HttpSession session) {     //TODO: Tree dem Spieler zuweisen falls er noch keinen hat
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        Player player = playerService.create(user.getUsername());
        userService.addPlayer(user.getId(), player);
        gameService.joinGame(id, player);
        /*
        if(!playerService.findTree(player.getId())){
            playerService.addTree(player.getId(), );
        };

         */
        session.setAttribute(PLAYER_SESSION_ID_KEY, player.getId());
        return ResponseEntity.ok("Server gejoint");
    }
}
