package com.baumstaemme.backend.game;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GameDto {

    private Long id;
    private String name;
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private Long mapId;
    private int MapSize;

    private List<Long> playerIds;
}
