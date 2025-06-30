package com.baumstaemme.backend.game.unit;

import com.baumstaemme.backend.game.tree.TreeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees/{treeId}/units")
public class UnitController {

    private final UnitService unitService;
    private final TreeService treeService;

    public UnitController(UnitService unitService, TreeService treeService) {
        this.unitService = unitService;
        this.treeService = treeService;
    }

    // Alle Einheiten in einem Dorf abrufen
    @GetMapping
    public ResponseEntity<List<Unit>> getUnits(@PathVariable Long treeId) {
        List<Unit> units = treeService.findById(treeId).getUnits();
        return ResponseEntity.ok(units);
    }

    // Einheiten rekrutieren
    @PostMapping("/recruit")
    public ResponseEntity<UnitQueueEntry> recruitUnits(@PathVariable Long treeId, @RequestBody UnitDto unitDto) {
        UnitType unitType = unitDto.getUnitType();
        int amount = unitDto.getAmount();

        if (unitType == null || amount <= 0) {
            return ResponseEntity.badRequest().build();
        }

        UnitQueueEntry queueEntry = unitService.recruitUnits(treeId, unitType, amount);
        return new ResponseEntity<>(queueEntry, HttpStatus.ACCEPTED);
    }

    // Alle ausstehenden Rekrutierungsaufträge für ein Dorf abrufen
    @GetMapping("/queue")
    public ResponseEntity<List<UnitQueueEntry>> getUnitQueue(@PathVariable Long treeId) {
        List<UnitQueueEntry> queue = unitService.getPendingUnitRecruitments(treeId);
        return ResponseEntity.ok(queue);
    }
}
