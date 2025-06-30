package com.baumstaemme.backend.game.movement;

import lombok.Data;

import java.util.List;

@Data
public class MovementDto {

    private Long originId;
    private Long targetId;

    private List<MovementUnitDto> unitsToSend;
}
