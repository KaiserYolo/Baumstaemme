package com.baumstaemme.backend.game.tree;


import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.baumstaemme.backend.game.upgrade.UpgradeType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService {

    private final TreeRepo treeRepo;

    public TreeService(TreeRepo treeRepo) {
        this.treeRepo = treeRepo;
    }

    Tree createTree(String name) {
        Tree tree = new Tree();
        tree.setName(name);
        return saveTree(tree);
    }

    List<Long> getIds() {
        return treeRepo.findAllIds();
    }

    public Tree getTree(long id) {
        return treeRepo.findById(id).get();
    }

    public int getBuildingLevel(long id, UpgradeType building) {
        return 0;
    }

    public Tree saveTree(Tree tree) { // Public???
        return treeRepo.save(tree);
    }

    public void addUpgrade(Upgrade upgrade) {

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
