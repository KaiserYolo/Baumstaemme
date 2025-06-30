package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.map.MapService;
import com.baumstaemme.backend.game.movement.MovementService;
import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.player.PlayerService;
import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.game.tree.TreeService;
import com.baumstaemme.backend.game.unit.UnitService;
import com.baumstaemme.backend.user.User;
import com.baumstaemme.backend.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameService {

    private final GameRepo gameRepo;
    private final MapService mapService;
    private final UserService userService;
    private final PlayerService playerService;
    private final TreeService treeService;
    private final UnitService unitService;
    private final MovementService movementService;

    public GameService(GameRepo gameRepo, MapService mapService, UserService userService, PlayerService playerService, TreeService treeService, UnitService unitService, MovementService movementService) {
        this.gameRepo = gameRepo;
        this.mapService = mapService;
        this.userService = userService;
        this.playerService = playerService;
        this.treeService = treeService;
        this.unitService = unitService;
        this.movementService = movementService;
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

    @Transactional
    public Player joinGame(Long id, Long userId) {
        Game game = findById(id);
        User user = userService.findById(userId);
        if (game == null || user == null) {
            return null;
        }



        Player player = game.getPlayers().stream().findFirst().filter(p ->
                p.getUser().getId().equals(userId)).orElse(null);

        if (player != null) {
            return player;
        }

        Tree tree = mapService.getFreeTree(game.getMap());

        player = new Player();
        player.setUser(user);
        player.setGame(game);

        tree.setOwner(player);
        player.getTrees().add(tree);

        return playerService.save(player);
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public synchronized void processGameTick() {
        treeService.leafProduction();
        treeService.processUpgrade();

        unitService.processUnitRecruitment();

        movementService.processIncomingAttacks();
    }
}