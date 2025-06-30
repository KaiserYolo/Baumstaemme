package com.baumstaemme.backend.game.movement;

import com.baumstaemme.backend.game.unit.UnitType;
import lombok.Data;

@Data
public class MovementUnitDto {

    private UnitType unitType;
    private int count;

}
