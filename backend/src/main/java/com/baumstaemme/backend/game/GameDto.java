package com.baumstaemme.backend.game;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class GameDto {

    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private int maxPlayers;

    private int mapSize;
}
