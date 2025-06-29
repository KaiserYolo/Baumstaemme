package com.baumstaemme.backend.game.tree;


import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.tile.Tile;
import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.baumstaemme.backend.game.upgrade.UpgradeType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class TreeService {

    private final TreeRepo treeRepo;

    public TreeService(TreeRepo treeRepo) {
        this.treeRepo = treeRepo;
    }

    public Tree create(Point position) {
        Tree tree = new Tree();
        tree.setName("Baum");
        tree.setOwner(null);
        tree.setPosition(position);
        return save(tree);
    }

    public Tree save(Tree tree) {
        return treeRepo.save(tree);
    }

    public Tree findById(Long id) {
        return treeRepo.findById(id).orElse(null);
    }

    List<Long> getIds() {
        return treeRepo.findAllIds();
    }

    public Tree findById(long id) {
        return treeRepo.findById(id).orElse(null);
    }

    public int getBuildingLevel(long id, UpgradeType building) {
        return 0;
    }

    public Tree saveTree(Tree tree) {
        return treeRepo.save(tree);
    }

    public void addUpgrade(Upgrade upgrade) {

    }

    public Tree setOwner(long id, Player player) {
        Tree tree = findById(id);
        if (tree == null) {
            return null;
        }
        tree.setOwner(player);
        return save(tree);
    }


    // Maybe eigener Service
    // Alle 60 Sekunden Ressourcen produzieren
    @Scheduled(fixedRate = 60000)
    void leafProduction() {
        List<Tree> trees = treeRepo.findAll();

        for (Tree tree : trees) {
            tree.setLeaves(tree.getLeaves() + tree.getLeavesProduction());
            saveTree(tree);
        }
        System.out.println("Productioncycle finished!");
    }
}
