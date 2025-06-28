package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.map.MapService;
import com.baumstaemme.backend.game.player.Player;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class GameService {

    private final GameRepo gameRepo;
    private final MapService mapService;

    public GameService(GameRepo gameRepo, MapService mapService) {
        this.gameRepo = gameRepo;
        this.mapService = mapService;
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

    public Game updateStatus(Long id, GameStatus status) {
        Game game = findById(id);
        game.setStatus(status);
        return save(game);
    }


    public Game joinGame(Long id, Player player) { //TODO: richtiger RückgabeTyp?
        Game game = findById(id);
        game.getPlayers().add(player);
        return gameRepo.save(game);
    }

    public Game removePlayer(Long id, Player player) {
        Game game = findById(id);
        game.getPlayers().remove(player);
        return gameRepo.save(game);
    }
}