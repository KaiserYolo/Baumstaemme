package com.baumstaemme.backend.upgrade;


import com.baumstaemme.backend.tree.Tree;
import com.baumstaemme.backend.tree.TreeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UpgradeService {

    private final UpgradeRepo upgradeRepo;
    private final TreeService treeService;

    public UpgradeService(UpgradeRepo upgradeRepo, TreeService treeService) {
        this.upgradeRepo = upgradeRepo;
        this.treeService = treeService;
    }

    public int getBuildingLevel(Tree tree, UpgradeBuilding building) {
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

    public Upgrade queueUpgrade(Tree tree, UpgradeBuilding building) {
        int currentLevel = getBuildingLevel(tree, building);
        int targetLevel = currentLevel + 1; //TODO: Was passiert wenn das Gebäude zweimal in die Queue kommt?

        //TODO: Eigene Funktion
        int cost = 50 * targetLevel; //TODO: Baukostenregel einführen
        long duration = 60 * targetLevel * 1000L; //TODO: Bauzeitenregel einführen
        //--

        //TODO: Eigene Funktion
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
            upgrade.setStartTime(System.currentTimeMillis() + tree.getUpgradeQueue().getLast().getEndTime());
            upgrade.setStatus(UpgradeStatus.QUEUED);

        }
        upgrade.setEndTime(upgrade.getStartTime() + duration);
        upgrade.setTree(tree);

        tree.getUpgradeQueue().add(upgrade);
        treeService.saveTree(tree);

        return upgrade;
    }

    public void startNextUpgrade(Tree tree) {
        Optional<Upgrade> nextUpgrade = tree.getUpgradeQueue()
                .stream()
                .filter(upgrade -> upgrade.getStatus() == UpgradeStatus.QUEUED)
                .findFirst();

        if (nextUpgrade.isPresent()) {
            Upgrade upgrade = nextUpgrade.get();
            upgrade.setStatus(UpgradeStatus.IN_PROGRESS);
            treeService.saveTree(tree);
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
                upgradeRepo.save(upgrade);

                //startNextUpgrade(tree);
            }
        }
    }
}
