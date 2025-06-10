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

    public UpgradeController(UpgradeService upgradeService) {
        this.upgradeService = upgradeService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Upgrade>> getAllUpgrades() {
        return ResponseEntity.ok(upgradeService.getAllUpgrades());
    }

    @PostMapping("/queue")
    public ResponseEntity<Upgrade> queueUpgrade(@RequestParam Long treeId, @RequestParam UpgradeType building) {
        Upgrade upgrade = upgradeService.queueUpgrade(treeId, building); //TODO
        return ResponseEntity.ok(upgrade);
    }
}
