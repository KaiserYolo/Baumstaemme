package com.baumstaemme.backend.game.player;

import com.baumstaemme.backend.game.tree.Tree;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepo playerRepo;

    public PlayerService(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    public Player save(Player player) {
        if (player == null) {
            return null;
        }
        return playerRepo.save(player);
    }


    public Player create(String name) {
        Player player = new Player();
        player.setUsername(name);
        return save(player);
    }

    public Player findById(Long id) {
        return playerRepo.findById(id).orElse(null);
    }

    public boolean findTree(Long id) {
        Player player = findById(id);
        List<Tree> trees = player.getTrees();
        return trees != null;
    }

    public void addTree(Long id, Tree tree){
        Player player = findById(id);
        List<Tree> trees = player.getTrees();
        trees.add(tree);
        player.setTrees(trees);
        playerRepo.save(player);
    }
}
