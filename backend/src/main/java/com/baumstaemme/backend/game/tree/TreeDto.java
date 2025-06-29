package com.baumstaemme.backend.game.tree;

import lombok.Data;

import java.awt.*;

@Data
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
