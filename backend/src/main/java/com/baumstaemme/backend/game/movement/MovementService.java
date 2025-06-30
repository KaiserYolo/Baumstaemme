package com.baumstaemme.backend.game.movement;

import com.baumstaemme.backend.game.report.Report;
import com.baumstaemme.backend.game.report.ReportRepo;
import com.baumstaemme.backend.game.report.ReportService;
import com.baumstaemme.backend.game.report.ReportType;
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
    private final ReportService reportService;

    public MovementService(MovementRepo movementRepo, MovementUnitRepo movementUnitRepo, TreeService treeService, ReportRepo reportRepo, UnitService unitService, ReportService reportService) {
        this.movementRepo = movementRepo;
        this.movementUnitRepo = movementUnitRepo;
        this.treeService = treeService;
        this.reportRepo = reportRepo;
        this.unitService = unitService;
        this.reportService = reportService;
    }

    public void delete(Movement movement) {
        movementRepo.delete(movement);
    }

    public void delete(MovementUnit movementUnit) {
        movementUnitRepo.delete(movementUnit);
    }

    public void deleteList(List<MovementUnit> movementUnits) {
        for (MovementUnit movementUnit : movementUnits) {
            delete(movementUnit);
        }
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
                simulateCombat(movement);
            } else if (movement.getMovementType() == MovementType.RETURN) {
                returnUnitsToVillage(movement);
            }
            movement.setMovementStatus(MovementStatus.ARRIVED); // Setze Status auf abgeschlossen
            save(movement);
        }
    }

    private void simulateCombat(Movement attack) {
        Tree attacker = attack.getOrigin();
        Tree defender = attack.getTarget();

        // Einheiten des Angreifers (die gesendeten Einheiten)
        List<MovementUnit> attackerUnits = attack.getUnits();
        // Einheiten des Verteidigers (aktuell im Dorf)
        List<Unit> defenderUnits = attack.getTarget().getUnits();

        // TODO: Implementiere hier eine detaillierte Kampfsimulation
        // Dies ist ein sehr vereinfachter Platzhalter!
        // Eine echte Simulation würde Faktoren wie Mauern, Verteidigungsboni,
        // Moral, Einheitentypen (Pikeniere gegen Kavallerie etc.) berücksichtigen.

        int attackerPower = attackerUnits.stream().mapToInt(unit -> unit.getUnitType().getAttack() * unit.getCount()).sum();
        int defenderPower = defenderUnits.stream().mapToInt(unit -> unit.getUnitType().getDefense() * unit.getCount()).sum();

        String combatReportContent;
        boolean isAlive = false;
        if (attackerPower > defenderPower * 1.2) { // Beispiel: Angreifer gewinnt deutlich
            isAlive = true;
            combatReportContent = "Der Angreifer hat mit " + attackerPower + " Angriffsstärke gegen den Verteidiger mit " + defenderPower + " Verteidigungsstärke klar gewonnen. Das Dorf wurde geplündert und ein Großteil der Verteidiger getötet.";
            // Verteidiger-Einheiten reduzieren (z.B. 80% Verlust)
            for (Unit dUnit : defenderUnits) {
                dUnit.setCount((int) (dUnit.getCount() * 0.2)); // 80% Verlust
                unitService.save(dUnit);
            }
            // Ressourcen plündern (implementiere Logik hier)
            // villageService.deductResources für Verteidiger, addResources für Angreifer

        } else if (defenderPower > attackerPower * 1.2) { // Verteidiger gewinnt deutlich
            combatReportContent = "Der Verteidiger hat mit " + defenderPower + " Verteidigungsstärke den Angriff mit " + attackerPower + " Angriffsstärke abgewehrt. Die Angreifer wurden größtenteils vernichtet.";
            // Angreifer-Einheiten werden vernichtet, daher keine Rückkehr
            deleteList(attackerUnits); // Alle Angreifer-Einheiten löschen

        } else { // Unentschieden oder knapper Kampf
            isAlive = true;
            combatReportContent = "Es war ein harter Kampf. Beide Seiten haben Verluste erlitten.";
            // Beide Seiten verlieren einen Teil der Einheiten (z.B. 50% Verlust)
            for (Unit dUnit : defenderUnits) {
                dUnit.setCount((int) (dUnit.getCount() * 0.5));
                unitService.save(dUnit);
            }
            for (MovementUnit aUnit : attackerUnits) {
                aUnit.setCount((int) (aUnit.getCount() * 0.5));
                save(aUnit); // Angreifer-Einheiten bleiben bestehen für Rückweg
            }
        }

        // Generiere Kampfbericht
        Report report = new Report();
        report.setPlayer(defender.getOwner()); // Bericht an Verteidiger
        report.setReportType(ReportType.COMBAT);
        report.setTitle("Kampfbericht: Angriff auf " + defender.getName());
        report.setContent(combatReportContent + "\nAngreifer: " + attacker.getName() + " (" + attacker.getOwner().getUser().getUsername() + ")" +
                "\nVerteidiger: " + defender.getName() + " (" + defender.getOwner().getUser().getUsername() + ")");
        reportService.save(report);

        // Optional: Einen Rückzugsbefehl für die verbleibenden Angreifer-Einheiten erstellen
        if (!attackerUnits.isEmpty() && isAlive) {
            sendReturnTrip(attack, attackerUnits);
        }
    }

    private void returnUnitsToVillage(Movement attack) {
        Tree originTree = attack.getOrigin();
        List<MovementUnit> returningUnits = attack.getUnits();

        for (MovementUnit retUnit : returningUnits) {
            Unit villageUnit = originTree.getUnits().stream().filter(unit -> unit.getUnitType() == retUnit.getUnitType()).findFirst()//unitRepository.findByVillageAndUnitType(originVillage, retUnit.getUnitType())
                    .orElseGet(() -> {
                        Unit newUnit = new Unit();
                        newUnit.setTree(originTree);
                        newUnit.setUnitType(retUnit.getUnitType());
                        newUnit.setCount(0);
                        return newUnit;
                    });
            villageUnit.setCount(villageUnit.getCount() + retUnit.getCount());
            unitService.save(villageUnit);
        }
        deleteList(returningUnits); // Einheiten sind zurück, AttackUnits löschen
    }

    private void sendReturnTrip(Movement originalAttack, List<MovementUnit> unitsToReturn) {
        Tree origin = originalAttack.getTarget(); // Ursprüngliches Ziel ist jetzt Startpunkt
        Tree target = originalAttack.getOrigin(); // Ursprünglicher Start ist jetzt Ziel

        // Berechne die Geschwindigkeit der verbleibenden Einheiten neu, falls Verluste aufgetreten sind
        int slowestReturnSpeed = Integer.MAX_VALUE;
        boolean hasReturningUnits = false;
        for(MovementUnit au : unitsToReturn) {
            if (au.getCount() > 0) {
                slowestReturnSpeed = Math.min(slowestReturnSpeed, au.getUnitType().getSpeed());
                hasReturningUnits = true;
            }
        }
        if (!hasReturningUnits) {
            return; // Keine Einheiten kehren zurück
        }

        double distance = calculateDistance(origin.getPosition(), target.getPosition());
        double travelTimeHours = distance / slowestReturnSpeed;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime arrivalTime = startTime.plusSeconds((long) (travelTimeHours * 3600));

        Movement returnAttack = new Movement();
        returnAttack.setOrigin(origin);
        returnAttack.setTarget(target);
        returnAttack.setMovementType(MovementType.RETURN);
        returnAttack.setStartTime(startTime);
        returnAttack.setArrivalTime(arrivalTime);
        returnAttack.setMovementStatus(MovementStatus.PENDING);
        returnAttack = save(returnAttack);

        for (MovementUnit au : unitsToReturn) {
            if (au.getCount() > 0) {
                // Erstelle neue AttackUnit-Einträge für den Rückweg
               MovementUnit newReturnUnit = new MovementUnit();
                newReturnUnit.setMovement(returnAttack);
                newReturnUnit.setUnitType(au.getUnitType());
                newReturnUnit.setCount(au.getCount());
                save(newReturnUnit);
            }
        }
    }

    // TODO: Diese Methode wird von einem Scheduler aufgerufen
    @Transactional
    public void processIncomingAttacks() {
        processArrivedMovements();
    }
}
