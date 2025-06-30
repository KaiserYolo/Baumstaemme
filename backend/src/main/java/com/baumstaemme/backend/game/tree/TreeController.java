package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.player.Player;
import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.baumstaemme.backend.game.upgrade.UpgradeDto;
import com.baumstaemme.backend.game.upgrade.UpgradeUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    private static final String PLAYER_SESSION_ID_KEY = "playerSession";

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<TreeDto> getTree(@PathVariable Long id, HttpSession session) {
        Long playerId = (Long) session.getAttribute(PLAYER_SESSION_ID_KEY);
        Tree tree = treeService.findById(id);
        return ResponseEntity.ok(TreeUtil.createResponseDto(tree, playerId));
    }

    @PostMapping("/{id}/upgrade")
    public ResponseEntity<UpgradeDto> upgrade(@PathVariable Long id, @RequestBody UpgradeDto requestDto, HttpSession session) {
        Long playerId = (Long) session.getAttribute(PLAYER_SESSION_ID_KEY);
        Tree tree = treeService.findById(id);

        Player owner = tree.getOwner();

        if (owner == null || !owner.getId().equals(playerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Upgrade upgrade = treeService.addUpgrade(tree, requestDto.getBuilding());
        UpgradeDto responseDto = UpgradeUtil.createResponseDto(upgrade);
        return ResponseEntity.ok(responseDto);
    }
}
