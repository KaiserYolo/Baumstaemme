package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.map.MapService;
import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.player.PlayerService;
import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.user.User;
import com.baumstaemme.backend.user.UserService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class GameService {

    private final GameRepo gameRepo;
    private final MapService mapService;
    private final UserService userService;
    private final PlayerService playerService;

    public GameService(GameRepo gameRepo, MapService mapService, UserService userService, PlayerService playerService) {
        this.gameRepo = gameRepo;
        this.mapService = mapService;
        this.userService = userService;
        this.playerService = playerService;
    }

    public Game save(Game game) {
        if (game == null) {
            return null;
        }
        return gameRepo.save(game);
    }

    public void delete(Long id) {
        if (id != null) {
            gameRepo.deleteById(id);
        }
    }

    public Game create(GameDto gameDto) {
        Game game = new Game();
        game.setName(gameDto.getName());
        game.setCreated(new Date());
        game.setStatus(GameStatus.CREATED);
        game.setMap(mapService.createMap(gameDto.getMapSize()));
        return save(game);
    }

    public Game findById(Long id) {
        return gameRepo.findById(id).orElse(null);
    }

    public List<Game> getAll() {
        return gameRepo.findAll();
    }

    public Game updateStatus(Long id, GameStatus status) {
        Game game = findById(id);
        game.setStatus(status);
        return save(game);
    }

    public Player joinGame(Long id, Long userId) {
        Game game = findById(id);
        if (game == null) {
            return null;
        }
        Player player = userService.addPlayer(userId);
        Tree tree = mapService.getFreeTree(game.getMap());
        player = playerService.addTree(player, tree);
        game.getPlayers().add(player);
        
        gameRepo.save(game);
        return player;
    }
}