package com.baumstaemme.backend.game.unit;

import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.game.tree.TreeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitService {

    private final UnitRepo unitRepo;
    private final UnitQueueEntryRepo unitQueueEntryRepo;
    private final TreeService treeService;

    public UnitService(UnitRepo unitRepo,UnitQueueEntryRepo unitQueueEntryRepo, TreeService treeService) {
        this.unitRepo = unitRepo;
        this.unitQueueEntryRepo = unitQueueEntryRepo;
        this.treeService = treeService;
    }

    public Unit save(Unit unit) {
        return unitRepo.save(unit);
    }

    public UnitQueueEntry save(UnitQueueEntry unitQueueEntry) {
        return unitQueueEntryRepo.save(unitQueueEntry);
    }

    public void delete(Unit unit) {
        unitRepo.delete(unit);
    }

    public void delete(UnitQueueEntry unitQueueEntry) {
        unitQueueEntryRepo.delete(unitQueueEntry);
    }

    public List<UnitQueueEntry> findAll() {
        return unitQueueEntryRepo.findAll();
    }

    @Transactional
    public UnitQueueEntry recruitUnits(Long villageId, UnitType unitType, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount of units to recruit must be positive.");
        }

        Tree tree = treeService.findById(villageId);

        //TODO Level "Kaserne/Trunk"

        int cost = unitType.getCost() * amount;

        try {
            treeService.deductResources(villageId, cost);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Not enough resources to recruit " + amount + " " + unitType.getName() + "(s).");
        }

        long duration = (long) unitType.getDuration() * amount;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(duration);

        UnitQueueEntry queueEntry = new UnitQueueEntry();
        queueEntry.setTree(tree);
        queueEntry.setUnitType(unitType);
        queueEntry.setAmount(amount);
        queueEntry.setStartTime(startTime);
        queueEntry.setEndTime(endTime);

        return save(queueEntry);
    }

    @Transactional
    public void completeUnitRecruitment(UnitQueueEntry queueEntry) {
        Tree tree = queueEntry.getTree();
        UnitType unitType = queueEntry.getUnitType();
        int amount = queueEntry.getAmount();

        Unit unit = tree.getUnits().stream().filter(t ->
            t.getUnitType().equals(unitType)).findFirst().orElseGet(() -> {
                Unit newUnit = new Unit();
                newUnit.setTree(tree);
                newUnit.setUnitType(unitType);
                newUnit.setCount(0);
                return newUnit;
            });
        unit.setCount(unit.getCount() + amount);
        save(unit);

        delete(queueEntry);
    }

    public List<UnitQueueEntry> getPendingUnitRecruitments(Long treeId) {
        return unitQueueEntryRepo.findAll().stream().filter(e -> e.getTree().getId().equals(treeId)).collect(Collectors.toList());
    }

    @Transactional
    public void processUnitRecruitment() {
        LocalDateTime now = LocalDateTime.now();
        List<UnitQueueEntry> completedEntries = findAll().stream().filter(e -> e.getStartTime().isAfter(now)).toList();
        for (UnitQueueEntry entry : completedEntries) {
            completeUnitRecruitment(entry);
        }
    }
}
