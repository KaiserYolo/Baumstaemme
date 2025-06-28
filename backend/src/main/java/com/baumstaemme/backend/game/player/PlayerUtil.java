package com.baumstaemme.backend.game.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtil {

    public static PlayerDto createResponseDto(Player player) {
        if (player == null) {
            return null;
        }
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(playerDto.getId());
        playerDto.setName(playerDto.getName());
        return playerDto;
    }

    public static List<PlayerDto> toDtoList(List<Player> players) {
        List<PlayerDto> playerDtoList = new ArrayList<>();
        for (Player player : players) {
            playerDtoList.add(createResponseDto(player));
        }
        return playerDtoList;
    }
}
