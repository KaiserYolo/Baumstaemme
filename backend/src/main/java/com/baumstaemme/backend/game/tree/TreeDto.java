package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.player.PlayerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
public class TreeDto {

    private Long id;
    private String name;

    private Point position;

    private Long ownerId;
    private String ownerName;

    private int leaves;
    private int leafProduction;

    private int trunk;
    private int bark;
    private int branches;
    private int root;
}
