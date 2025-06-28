package com.baumstaemme.backend.game.tree;

import com.baumstaemme.backend.game.player.PlayerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TreeDto {

    private Long id;
    private String name;

    private PlayerDto owner;

    private int leaves;
    private int leavesProduction;
}
