package com.baumstaemme.backend.game;


import com.baumstaemme.backend.game.map.Map;
import com.baumstaemme.backend.game.map.MapService;
import com.baumstaemme.backend.game.player.Player;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameService {

    private final GameRepo gameRepo;
    private final MapService mapService;

    public GameService(GameRepo gameRepo, MapService mapService) {
        this.gameRepo = gameRepo;
        this.mapService = mapService;
    }


    public Game save(Game game) {
        return gameRepo.save(game);
    }

    public void delete(Long id) {
        gameRepo.deleteById(id);
    }

    public Game create(GameDto gameDto) {
        Game game = new Game();
        game.setName(gameDto.getName());
        game.setCreated(new Date());
        game.setStatus(GameStatus.CREATED);
        game.setMap(mapService.createMap(gameDto.getMapSize()));
        return save(game);
    }

    public Game create() {
        return create(null);
    }

    public List<Long> getAllIds() {
        return gameRepo.findAllIds();
    }

    public Game findById(Long id) {
        return gameRepo.findById(id).orElse(null);
    }

    public List<Player> getPlayers(Long id) {
        return findById(id).getPlayers();
    }

    public Map getMap(Long id) {
        return findById(id).getMap();
    }

    public Game updateStatus(Long id, GameStatus status) {
        Game game = findById(id);
        game.setStatus(status);
        return save(game);
    }

    public Game addPlayer(Long id, Player player) {
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