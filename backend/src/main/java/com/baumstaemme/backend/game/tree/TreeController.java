package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.upgrade.Upgrade;
import com.baumstaemme.backend.game.upgrade.UpgradeDto;
import com.baumstaemme.backend.game.upgrade.UpgradeType;
import com.baumstaemme.backend.game.upgrade.UpgradeUtil;
import jakarta.servlet.http.HttpSession;
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
        if (tree == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TreeUtil.createResponseDto(tree, playerId));
    }

    @PostMapping("/{id}/upgrade")
    public ResponseEntity<UpgradeDto> upgrade(@PathVariable Long id, @RequestBody UpgradeDto requestDto, HttpSession session) {
        Long playerId = (Long) session.getAttribute(PLAYER_SESSION_ID_KEY);
        Tree tree = treeService.findById(id);
        if (tree == null || !playerId.equals(tree.getOwner().getId())) {
            return ResponseEntity.notFound().build();
        }
        Upgrade upgrade = treeService.addUpgrade(tree, requestDto.getBuilding());
        UpgradeDto responseDto = UpgradeUtil.createResponseDto(upgrade);
        return ResponseEntity.ok(responseDto);
    }
}
