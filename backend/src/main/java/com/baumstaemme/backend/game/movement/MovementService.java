package com.baumstaemme.backend.game.movement;

import com.baumstaemme.backend.game.report.ReportRepo;
import com.baumstaemme.backend.game.tree.Tree;
import com.baumstaemme.backend.game.tree.TreeService;
import com.baumstaemme.backend.game.unit.Unit;
import com.baumstaemme.backend.game.unit.UnitService;
import com.baumstaemme.backend.game.unit.UnitType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovementService {

    private final MovementRepo movementRepo;
    private final MovementUnitRepo movementUnitRepo;
    private final TreeService treeService;
    private final ReportRepo reportRepo;
    private final UnitService unitService;

    public MovementService(MovementRepo movementRepo, MovementUnitRepo movementUnitRepo, TreeService treeService, ReportRepo reportRepo, UnitService unitService) {
        this.movementRepo = movementRepo;
        this.movementUnitRepo = movementUnitRepo;
        this.treeService = treeService;
        this.reportRepo = reportRepo;
        this.unitService = unitService;
    }

    public Movement save(Movement movement) {
        return movementRepo.save(movement);
    }

    public MovementUnit save(MovementUnit movementUnit) {
        return movementUnitRepo.save(movementUnit);
    }

    public List<Movement> findAll() {
        return movementRepo.findAll();
    }

    public Movement findById(Long id) {
        return movementRepo.findById(id).orElse(null);
    }

    private double calculateDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    @Transactional
    public Movement sendAttack(Long originTreeId, Long targetTreeId, List<MovementUnitDto> unitsToSend) {
        Tree originTree = treeService.findById(originTreeId);
        Tree targetTree = treeService.findById(targetTreeId);

        if (originTreeId.equals(targetTreeId)) {
            throw new IllegalArgumentException("Cannot attack your own tree");
        }

        List<Unit> availableUnits = originTree.getUnits();

        List<MovementUnit> sentUnits = new ArrayList<>();

        int slowestSpeed = Integer.MAX_VALUE;

        for (MovementUnitDto movementUnitDto : unitsToSend) {
            UnitType unitType = movementUnitDto.getUnitType();
            int amount = movementUnitDto.getCount();

            if (amount <= 0) continue;

            int available = availableUnits.stream()
                    .filter(unit -> unit.getUnitType() == unitType)
                    .mapToInt(Unit::getCount).sum();

            if (available < amount) {
                throw new IllegalArgumentException("Not enough " + unitType.getName() +
                        " in tree " + originTree.getName() +
                        ". Available: " + available +
                        ", Needed: " + amount);
            }

            Unit unitInVillage = availableUnits.stream().filter(unit -> unit.getUnitType() == unitType)
                    .findFirst().orElseThrow(() ->
                            new IllegalArgumentException("Unit type " + unitType + " not found in tree.")
                    );
            unitInVillage.setCount(unitInVillage.getCount() - amount);
            unitService.save(unitInVillage);

            MovementUnit attackUnit = new MovementUnit();
            attackUnit.setUnitType(unitType);
            attackUnit.setCount(amount);
            sentUnits.add(attackUnit);

            slowestSpeed = Math.min(slowestSpeed, unitType.getSpeed());
        }
        if (slowestSpeed == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("No valid units specified for attack.");
        }

        double distance = calculateDistance(originTree.getPosition(), targetTree.getPosition());

        double travelTimeHours = distance / slowestSpeed;

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime arrivalTime = startTime.plusSeconds((long) (travelTimeHours * 3600));

        Movement attack = new Movement();
        attack.setOrigin(originTree);
        attack.setTarget(targetTree);
        attack.setMovementType(MovementType.ATTACK);
        attack.setStartTime(startTime);
        attack.setArrivalTime(arrivalTime);
        attack.setMovementStatus(MovementStatus.PENDING);
        attack = save(attack);

        for (MovementUnit movementUnit : sentUnits) {
            movementUnit.setMovement(attack);
            save(movementUnit);
        }

        return attack;
    }

    @Transactional
    public void processArrivedMovements() {
        LocalDateTime now = LocalDateTime.now();
        List<Movement> arrivedMovements = findAll().stream().filter(movement -> movement.getArrivalTime().isAfter(now)).toList();

        for (Movement movement : arrivedMovements) {
            if (movement.getMovementType() == MovementType.ATTACK) {
                //simulateCombat(movement);
            }
            movement.setMovementStatus(MovementStatus.ARRIVED); // Setze Status auf abgeschlossen
            save(movement);
        }
    }
    /*
    private void simulateCombat(Movement attack) {
        Tree attacker = attack.getOrigin();
        Tree defender = attack.getTarget();

        // Einheiten des Angreifers (die gesendeten Einheiten)
        List<MovementUnit> attackerUnits = ;
        // Einheiten des Verteidigers (aktuell im Dorf)
        List<Unit> defenderUnits = unitRepository.findByVillage(defenderVillage);

        // TODO: Implementiere hier eine detaillierte Kampfsimulation
        // Dies ist ein sehr vereinfachter Platzhalter!
        // Eine echte Simulation würde Faktoren wie Mauern, Verteidigungsboni,
        // Moral, Einheitentypen (Pikeniere gegen Kavallerie etc.) berücksichtigen.

        long attackerPower = attackerUnits.stream()
                .mapToLong(au -> (long) au.getCount() * UNIT_ATTACK_POWER.getOrDefault(au.getUnitType(), 0))
                .sum();
        long defenderPower = defenderUnits.stream()
                .mapToLong(du -> (long) du.getCount() * UNIT_DEFENSE_POWER.getOrDefault(du.getUnitType(), 0))
                .sum();

        String combatReportContent;
        if (attackerPower > defenderPower * 1.2) { // Beispiel: Angreifer gewinnt deutlich
            combatReportContent = "Der Angreifer hat mit " + attackerPower + " Angriffsstärke gegen den Verteidiger mit " + defenderPower + " Verteidigungsstärke klar gewonnen. Das Dorf wurde geplündert und ein Großteil der Verteidiger getötet.";
            // Verteidiger-Einheiten reduzieren (z.B. 80% Verlust)
            for (Unit dUnit : defenderUnits) {
                dUnit.setCount((int) (dUnit.getCount() * 0.2)); // 80% Verlust
                unitRepository.save(dUnit);
            }
            // Ressourcen plündern (implementiere Logik hier)
            // villageService.deductResources für Verteidiger, addResources für Angreifer

        } else if (defenderPower > attackerPower * 1.2) { // Verteidiger gewinnt deutlich
            combatReportContent = "Der Verteidiger hat mit " + defenderPower + " Verteidigungsstärke den Angriff mit " + attackerPower + " Angriffsstärke abgewehrt. Die Angreifer wurden größtenteils vernichtet.";
            // Angreifer-Einheiten werden vernichtet, daher keine Rückkehr
            attackUnitRepository.deleteAll(attackerUnits); // Alle Angreifer-Einheiten löschen

        } else { // Unentschieden oder knapper Kampf
            combatReportContent = "Es war ein harter Kampf. Beide Seiten haben Verluste erlitten.";
            // Beide Seiten verlieren einen Teil der Einheiten (z.B. 50% Verlust)
            for (Unit dUnit : defenderUnits) {
                dUnit.setCount((int) (dUnit.getCount() * 0.5));
                unitRepository.save(dUnit);
            }
            for (AttackUnit aUnit : attackerUnits) {
                aUnit.setCount((int) (aUnit.getCount() * 0.5));
                attackUnitRepository.save(aUnit); // Angreifer-Einheiten bleiben bestehen für Rückweg
            }
        }

        // Generiere Kampfbericht
        Report report = new Report();
        report.setUser(defenderVillage.getUser()); // Bericht an Verteidiger
        report.setReportType(Report.ReportType.COMBAT);
        report.setTitle("Kampfbericht: Angriff auf " + defenderVillage.getName());
        report.setContent(combatReportContent + "\nAngreifer: " + attackerVillage.getName() + " (" + attackerVillage.getUser().getUsername() + ")" +
                "\nVerteidiger: " + defenderVillage.getName() + " (" + defenderVillage.getUser().getUsername() + ")");
        reportRepository.save(report);

        // Optional: Einen Rückzugsbefehl für die verbleibenden Angreifer-Einheiten erstellen
        if (!attackerUnits.isEmpty() && attackUnitRepository.findByAttack_Id(attack.getId()).stream().anyMatch(au -> au.getCount() > 0)) {
            sendReturnTrip(attack, attackerUnits);
        }
    }
    */
}
