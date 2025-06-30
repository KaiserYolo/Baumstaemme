package com.baumstaemme.backend.game.unit;

import lombok.Data;

@Data
public class UnitDto {

    private UnitType unitType;
    private int amount;
}
