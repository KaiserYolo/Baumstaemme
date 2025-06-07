package com.baumstaemme.backend.upgrade;

import com.baumstaemme.backend.tree.Tree;
import com.baumstaemme.backend.tree.TreeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/upgrade")
public class UpgradeController {

    private final UpgradeService upgradeService;
    private final TreeRepo treeRepo;

    public UpgradeController(UpgradeService upgradeService, TreeRepo treeRepo) {
        this.upgradeService = upgradeService;
        this.treeRepo = treeRepo;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Upgrade>> getAllUpgrades() {
        return ResponseEntity.ok(upgradeService.getAllUpgrades());
    }

    @PostMapping("/queue")
    public ResponseEntity<Upgrade> queueUpgrade(@RequestParam Long treeId, @RequestParam UpgradeBuilding building) {
        Tree tree = treeRepo.findById(treeId).orElseThrow();
        Upgrade upgrade = upgradeService.queueUpgrade(tree, building);
        treeRepo.save(tree);
        return ResponseEntity.ok(upgrade);
    }
}
