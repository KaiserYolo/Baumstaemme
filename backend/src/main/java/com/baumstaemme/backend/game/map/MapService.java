package com.baumstaemme.backend.game.map;

import com.baumstaemme.backend.game.tile.Tile;
import com.baumstaemme.backend.game.tile.TileService;
import com.baumstaemme.backend.game.tile.TileType;
import com.baumstaemme.backend.game.tree.Tree;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class MapService {

    private final MapRepo mapRepo;
    private final TileService tileService;

    MapService(MapRepo mapRepo, TileService tileService) {
        this.mapRepo = mapRepo;
        this.tileService = tileService;
    }

    public Map createMap(int mapSize) {
        Map map = new Map();
        map.setSize(mapSize);

        int maxTrees = mapSize*mapSize/10;

        Random random = new Random();
        HashSet<Point> coords = new HashSet<>();

        while (coords.size() < maxTrees) {
            int x = random.nextInt(map.getSize());
            int y = random.nextInt(map.getSize());

            Point cord = new Point(x, y);
            coords.add(cord);
        }

        List<Tile> tiles = new ArrayList<>();

        for (Point coord : coords) {
            Tile tile = tileService.create(coord); //TODO

            tiles.add(tile);
        }
        map.setTiles(tiles);
        return saveMap(map);
    }

    Map findById(long id) {
        return mapRepo.findById(id).orElse(null);
    }

    Map saveMap(Map map) {
        return mapRepo.save(map);
    }

    public Tree getFreeTree(Map map) {
        if (map == null) {
            return null;
        }
        List<Tile> tiles = map.getTiles().stream().filter(tile -> tile.getType() == TileType.TREE).toList();
        List<Tree> freeTrees = new ArrayList<>();
        for (Tile tile : tiles) {
            Tree tree = tile.getTree();
            if (tree.getOwner() == null) {
                freeTrees.add(tree);
            }
        }
        Random rand = new Random();
        return freeTrees.get(rand.nextInt(freeTrees.size()));
    }
}
