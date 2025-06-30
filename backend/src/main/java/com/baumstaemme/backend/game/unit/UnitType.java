package com.baumstaemme.backend.game.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UnitType {
    ANT("Ameise", 10, 10, 10, 5, 10),    //Def
    WASP("Wespe", 15, 15, 5, 15, 5),   //Off
    FLY("Fliege", 5, 10,15, 0, 0);    //Scout

    private final String name;
    private final int cost;
    private final int duration;

    private final int speed; // Tiles per hour
    private final int attack;
    private final int defense;
}
