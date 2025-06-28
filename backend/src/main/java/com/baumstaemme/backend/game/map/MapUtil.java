package com.baumstaemme.backend.game.map;

import com.baumstaemme.backend.game.tile.TileUtil;

public class MapUtil {      //TODO: Maybe sachen fehlen

    public static MapDto createResponseDto(Map map, Long playerId) {
        if (map == null) {
            return null;
        }
        MapDto mapDto = new MapDto();
        mapDto.setId(map.getId());
        mapDto.setSize(map.getSize());
        mapDto.setTiles(TileUtil.createResponseDto(map.getTiles(), playerId));
        return mapDto;
    }
}
