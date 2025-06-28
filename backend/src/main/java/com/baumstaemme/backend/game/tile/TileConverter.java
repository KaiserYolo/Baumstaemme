package com.baumstaemme.backend.game.tile;

import com.baumstaemme.backend.game.tree.TreeConverter;

import java.util.ArrayList;
import java.util.List;

public class TileConverter {

    public static TileDto toDto(Tile tile) {
        if (tile == null) {
            return null;
        }
        TileDto tileDto = new TileDto();
        tileDto.setId(tile.getId());
        tileDto.setPosition(tile.getPosition());
        tileDto.setType(tile.getType());
        tileDto.setTree(TreeConverter.toDto(tile.getTree()));
        return tileDto;
    }

    public static List<TileDto> toDtoList(List<Tile> tiles) {
        List<TileDto> tileDtoList = new ArrayList<>();
        for (Tile tile : tiles) {
            tileDtoList.add(toDto(tile));
        }
        return tileDtoList;
    }
}
