package com.baumstaemme.backend.game.tile;

import com.baumstaemme.backend.game.tree.TreeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
public class TileDto {

    private Long id;
    private Point position;

    private TileType type;

    private Boolean ownership;

    private TreeDto tree;
}
