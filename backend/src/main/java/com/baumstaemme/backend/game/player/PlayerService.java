package com.baumstaemme.backend.game.player;

import com.baumstaemme.backend.game.tree.Tree;

import com.baumstaemme.backend.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public Player create(User user) {
        Player player = new Player();
        player.setUser(user);
        return save(player);
    }

    public Player findById(Long id) {
        return playerRepo.findById(id).orElse(null);
    }


    public Player addTree(Player player, Tree tree){
        if (player == null) {
            return null;
        }
        if (player.getTrees() == null) {
            player.setTrees(new ArrayList<>());
        } else {
            player.getTrees().add(tree);
        }
        tree.setOwner(player);
        return save(player);
    }
}
