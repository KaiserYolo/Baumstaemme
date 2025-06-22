package com.baumstaemme.backend.game.upgrade;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum UpgradeType {

    TRUNK("Stamm",100, 30),
    BARK("Rinde", 100, 30),
    BRANCHES("Äste", 100, 30),
    ROOT("Wurzel", 100, 30);

    @Getter
    private final String buildingName;
    private final int baseCost;
    private final int baseDuration;


    public int getCost(int level) {
        return baseCost * level;
    }

    public int getDuration(int level) {
        return baseDuration * level;
    }

}
