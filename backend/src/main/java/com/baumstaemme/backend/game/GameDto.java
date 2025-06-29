package com.baumstaemme.backend.game;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class GameDto {

    private Long id;
    private String name;
    private Date created;
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private Long mapId;

    private List<Long> playerIds;

    private int MapSize;
}
