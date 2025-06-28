package com.baumstaemme.backend.game.map;

import com.baumstaemme.backend.game.tile.TileConverter;

public class MapConverter {

    public static MapDto toDto(Map map) {
        if (map == null) {
            return null;
        }
        MapDto mapDto = new MapDto();
        mapDto.setId(map.getId());
        mapDto.setSize(map.getSize());
        mapDto.setTiles(TileConverter.toDtoList(map.getTiles()));
        return mapDto;
    }
}
