package com.baumstaemme.backend.game.map;

import com.baumstaemme.backend.game.tile.Tile;
import com.baumstaemme.backend.game.tile.TileDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class MapDto{

    private Long id;
    private int size;

    private List<TileDto> tiles;
}
