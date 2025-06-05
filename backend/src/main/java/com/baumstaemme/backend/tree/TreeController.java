package com.baumstaemme.backend.tree;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Long>> getAllTrees() {
        return ResponseEntity.ok(treeService.getIds());
    }

    @GetMapping("/getById")
    public ResponseEntity<Tree> getTree(@RequestParam long id) {
        return ResponseEntity.ok(treeService.getTree(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Tree> createTree(@RequestParam String name) {
        return ResponseEntity.ok(treeService.createTree(name));
    }
}
