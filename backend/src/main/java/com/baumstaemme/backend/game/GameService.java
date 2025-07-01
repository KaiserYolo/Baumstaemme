package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.map.MapService;
import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.player.PlayerRepo;
import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.user.User;
import com.baumstaemme.backend.user.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepo gameRepo;
    private final PlayerRepo playerRepo;
    private final MapService mapService;
    private final UserService userService;

    public GameService(GameRepo gameRepo, MapService mapService, UserService userService, PlayerRepo playerRepo) {
        this.gameRepo = gameRepo;
        this.mapService = mapService;
        this.userService = userService;
        this.playerRepo = playerRepo;
    }

    public Game findById(Long id) {
        return gameRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found."));
    }

    public Boolean existsByName(String name) {
        return gameRepo.existsByName(name);
    }

    public List<Game> findAll() {
        return gameRepo.findAll();
    }

    public Game saveGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null.");
        }
        return gameRepo.save(game);
    }

    public Game create(String name, int mapSize) {
        if (existsByName(name)) {
            throw new EntityExistsException("Game with name " + name + " already exists.");
        }
        Game game = new Game();
        game.setName(name);
        game.setStatus(GameStatus.CREATED);
        game.setMap(mapService.createMap(mapSize));
        return saveGame(game);
    }

    @Transactional
    public Player joinGame(Long id, Long userId) {
        Game game = findById(id);
        User user = userService.findById(userId);

        Optional<Player> existingPlayer = game.getPlayers().stream()
                .filter(p -> p.getId().equals(userId))
                .findFirst();
        if (existingPlayer.isPresent()) {
            return existingPlayer.get();
        }

        Tree tree = mapService.getFreeTree(game.getMap());

        // Player Erstellung ist hier angenehmer
        Player player = new Player();
        player.setUser(user);
        player.setGame(game);
        player.getTrees().add(tree);

        game.getPlayers().add(player);
        tree.setOwner(player);

        return playerRepo.save(player);
    }
}