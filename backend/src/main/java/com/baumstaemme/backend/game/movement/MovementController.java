package com.baumstaemme.backend.game.movement;

import com.baumstaemme.backend.game.report.Report;
import com.baumstaemme.backend.game.report.ReportRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attacks")
public class MovementController {

    private final MovementService movementService;
    private final MovementRepo movementRepo;
    private final ReportRepo reportRepo;

    public MovementController(MovementService movementService, MovementRepo movementRepo, ReportRepo reportRepo) {
        this.movementService = movementService;
        this.movementRepo = movementRepo;
        this.reportRepo = reportRepo;
    }

    // Endpoint zum Senden eines Angriffs
    @PostMapping("/send")
    public ResponseEntity<Movement> sendAttack(@RequestBody MovementDto movementDto) {
        Long originVillageId = movementDto.getOriginId();
        Long targetVillageId = movementDto.getTargetId();
        List<MovementUnitDto> unitsToSend = movementDto.getUnitsToSend();

        if (originVillageId == null || targetVillageId == null || unitsToSend == null || unitsToSend.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Movement attack = movementService.sendAttack(originVillageId, targetVillageId, unitsToSend);
        return new ResponseEntity<>(attack, HttpStatus.ACCEPTED);
    }

    // Endpoint, um alle ausgehenden Angriffe eines Dorfes zu sehen
    @GetMapping("/outgoing/{villageId}")
    public ResponseEntity<List<Movement>> getOutgoingAttacks(@PathVariable Long villageId) {
        List<Movement> attacks = movementRepo.findAll().stream().filter(attack -> attack.getOrigin().getId().equals(villageId)).collect(Collectors.toList());
        return ResponseEntity.ok(attacks);
    }

    // Endpoint, um alle eingehenden Angriffe eines Dorfes zu sehen
    // ACHTUNG: Für ein echtes Spiel sollte dies nur für den Spieler selbst sichtbar sein!
    @GetMapping("/incoming/{treeId}")
    public ResponseEntity<List<Movement>> getIncomingAttacks(@PathVariable Long treeId) {
        List<Movement> attacks = movementRepo.findAll().stream().filter(attack -> attack.getTarget().getId().equals(treeId)).collect(Collectors.toList());
        return ResponseEntity.ok(attacks);
    }

    // Endpoint, um alle Berichte für einen Benutzer abzurufen (z.B. Kampfberichte)
    @GetMapping("/reports/my")
    public ResponseEntity<List<Report>> getMyReports(/* @AuthenticationPrincipal UserDetails userDetails */) {
        Long dummyUserId = 1L; // Temporär
        List<Report> reports = reportRepo.findAll().stream().filter(report -> report.getPlayer().getId().equals(dummyUserId)).collect(Collectors.toList());
        return ResponseEntity.ok(reports);
    }

    // Endpoint, um einen spezifischen Bericht zu lesen
    @GetMapping("/reports/{reportId}")
    public ResponseEntity<Report> getReportById(@PathVariable Long reportId) {
        return reportRepo.findById(reportId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
