package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.upgrade.UpgradeDto;
import lombok.Data;

import java.awt.*;
import java.util.List;


@Data
public class TreeDto {

    private Long id;
    private String name;

    private Point position;

    private Long ownerId;
    private String ownerName;

    private DtoType dtoType;

    private int leaves;
    private int leafProduction;

    private int trunk;
    private int bark;
    private int branches;
    private int root;

    private List<UpgradeDto> upgradeInfo;

    private UpgradeDto upgrade;

    public enum DtoType {
        PUBLIC,
        PRIVATE
    }
}
