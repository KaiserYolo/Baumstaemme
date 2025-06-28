package com.baumstaemme.backend.game;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class GameDto {

    private Long id;
    private String name;
    private Date created;
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private int maxPlayers;
    private int mapSize;
    private int treeDensity;
}
