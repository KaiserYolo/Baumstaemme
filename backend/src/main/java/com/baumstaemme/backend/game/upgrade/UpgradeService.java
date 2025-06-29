package com.baumstaemme.backend.game.upgrade;


import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.game.tree.TreeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UpgradeService {

    private final UpgradeRepo upgradeRepo;
    private final TreeService treeService;

    public UpgradeService(UpgradeRepo upgradeRepo, TreeService treeService) {
        this.upgradeRepo = upgradeRepo;
        this.treeService = treeService;
    }

    public int getBuildingLevel(Tree tree, UpgradeType building) {
        return switch (building) {
            case TRUNK -> tree.getTrunk();
            case BARK -> tree.getBark();
            case BRANCHES -> tree.getBranches();
            case ROOT -> tree.getRoot();
        };
    }

    public List<Upgrade> getAllUpgrades() {
        return upgradeRepo.findAll();
    }

    public List<Upgrade> getUpgrades(Tree tree) { // Util ?
        return tree.getUpgradeQueue();
    }

    public boolean isEmpty(Tree tree) { // Util??
        return tree.getUpgradeQueue().isEmpty();
    }

    public Upgrade queueUpgrade(long treeId, UpgradeType building) {

        Tree tree = treeService.findById(treeId);

        List<Upgrade> sameTypeUpgrades = getUpgrades(tree)
                .stream()
                .filter(upgrade -> upgrade.getBuilding() == building)
                .toList();

        int currentLevel;
        if (sameTypeUpgrades.isEmpty()) {
            currentLevel = getBuildingLevel(tree, building);
        } else {
            currentLevel = sameTypeUpgrades.getLast().getTargetLevel();
        }
        int targetLevel = currentLevel + 1;


        int cost = building.getCost(targetLevel);
        long duration = building.getDuration(targetLevel) * 1000L;


        int currentLeaves = tree.getLeaves();
        if (currentLeaves < cost) throw new IllegalArgumentException("Not enough leaves: " + tree.getLeaves());
        tree.setLeaves(currentLeaves - cost);

        Upgrade upgrade = new Upgrade();
        upgrade.setBuilding(building);
        upgrade.setTargetLevel(targetLevel);
        upgrade.setDuration(duration);
        if (isEmpty(tree)) {
            upgrade.setStartTime(System.currentTimeMillis());
            upgrade.setStatus(UpgradeStatus.IN_PROGRESS);
        } else {
            upgrade.setStartTime(tree.getUpgradeQueue().getLast().getEndTime());
            upgrade.setStatus(UpgradeStatus.QUEUED);

        }
        upgrade.setEndTime(upgrade.getStartTime() + duration);
        upgrade.setTree(tree);


        return upgradeRepo.save(upgrade);
    }

    public void startNextUpgrade(Tree tree) {
        List<Upgrade> upgrades = getUpgrades(tree);

        Upgrade nextUpgrade = upgrades
                .stream()
                .filter(upgrade -> upgrade.getStatus() == UpgradeStatus.QUEUED)
                .findFirst()
                .orElse(null);

        if (nextUpgrade != null) {
            nextUpgrade.setStatus(UpgradeStatus.IN_PROGRESS);
            upgradeRepo.save(nextUpgrade);
        }
    }

    @Scheduled(fixedRate = 10000)
    public void processQueue() {
        List<Upgrade> upgrades = upgradeRepo.findByStatus(UpgradeStatus.IN_PROGRESS);

        long now = System.currentTimeMillis();

        for (Upgrade upgrade : upgrades) {
            if (upgrade.getEndTime() <= now) {
                Tree tree = upgrade.getTree();
                switch (upgrade.getBuilding()) {
                    case TRUNK -> tree.setTrunk(upgrade.getTargetLevel());
                    case BARK -> tree.setBark(upgrade.getTargetLevel());
                    case BRANCHES -> tree.setBranches(upgrade.getTargetLevel());
                    case ROOT -> tree.setRoot(upgrade.getTargetLevel());
                }
                treeService.saveTree(tree);

                upgrade.setStatus(UpgradeStatus.DONE);

                startNextUpgrade(upgradeRepo.save(upgrade).getTree());

            }
        }
    }
}
