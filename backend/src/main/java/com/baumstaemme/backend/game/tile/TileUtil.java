package com.baumstaemme.backend.game.tile;

import com.baumstaemme.backend.game.tree.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class TileUtil {

    public static TileDto createResponseDto(Tile tile, Long playerId) {
        if (tile == null) {
            return null;
        }
        TileDto tileDto = new TileDto();
        tileDto.setId(tile.getId());
        tileDto.setPosition(tile.getPosition());
        tileDto.setType(tile.getType());
        tileDto.setTree(TreeUtil.createResponseDto(tile.getTree(), playerId));
        return tileDto;
    }

    public static List<TileDto> createResponseDto(List<Tile> tiles, Long playerId) {
        List<TileDto> tileDtoList = new ArrayList<>();
        for (Tile tile : tiles) {
            tileDtoList.add(createResponseDto(tile, playerId));
        }
        return tileDtoList;
    }
}
