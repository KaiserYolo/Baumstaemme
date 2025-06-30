package com.baumstaemme.backend.game.upgrade;

import lombok.Data;

@Data
public class UpgradeDto {

    private Long id;
    private UpgradeType building;
    private int targetLevel;

    private int cost;

    private long duration;
    private long startTime;
    private long endTime;

    private UpgradeStatus status;
}
