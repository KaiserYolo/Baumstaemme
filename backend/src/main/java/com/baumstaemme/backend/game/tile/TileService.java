package com.baumstaemme.backend.game.tile;


import com.baumstaemme.backend.game.tree.TreeService;
import org.springframework.stereotype.Service;

import java.awt.*;


@Service
public class TileService {

    TileRepo tileRepo;
    TreeService treeService;

    public TileService(TileRepo tileRepo, TreeService treeService) {
        this.tileRepo = tileRepo;
        this.treeService = treeService;
    }


    public Tile save(Tile tile) {
        return tileRepo.save(tile);
    }

    public Tile create(Point coord) {
        Tile tile = new Tile();
        tile.setPosition(coord);
        tile.setType(TileType.TREE);
        tile.setTree(treeService.create());
        return save(tile);
    }
}
