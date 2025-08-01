package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.baumstaemme.backend.game.upgrade.UpgradeService;
import com.baumstaemme.backend.game.upgrade.UpgradeType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class TreeService {

    private final TreeRepo treeRepo;
    private final UpgradeService upgradeService;

    public TreeService(TreeRepo treeRepo, UpgradeService upgradeService) {
        this.treeRepo = treeRepo;
        this.upgradeService = upgradeService;
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
        return treeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tree with id " + id + " not found."));
    }

    public Upgrade addUpgrade(Tree tree, UpgradeType upgradeType) {
        int currentLevel = tree.getBuildingLevel(upgradeType);
        Upgrade upgrade = upgradeService.createUpgrade(currentLevel, upgradeType);

        int leaves = tree.getLeaves();
        int cost = upgrade.getCost();
        if (leaves < cost) {
            return null;
        }
        tree.setLeaves(leaves - cost);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + upgrade.getDuration() * 1000L;
        upgrade.setStartTime(startTime);
        upgrade.setEndTime(endTime);

        tree.setUpgrade(upgrade);
        save(tree);

        return upgrade;
    }

    // TODO: WEBSOCKETS
    private void leafProduction() {
        List<Tree> trees = treeRepo.findAll();

        for (Tree tree : trees) {
            tree.setLeaves(tree.getLeaves() + tree.getLeavesProduction());
            save(tree);
        }
    }


    private void processUpgrade() {
        long now = System.currentTimeMillis();
        List<Tree> trees = treeRepo.findAll().stream().filter(tree -> tree.getUpgrade() != null).toList();
        for (Tree tree : trees) {
            Upgrade upgrade = tree.getUpgrade();
            if (upgrade.getEndTime() <= now) {
                tree.addBuildingLevel(upgrade.getBuilding());
                tree.setUpgrade(null);
                treeRepo.save(tree);
                System.out.println("Upgrade für Tree ID: " + tree.getId() + " abgeschlossen und entfernt.");
            }
        }
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public synchronized  void processGameTick() {
        leafProduction();
        processUpgrade();
    }
}
