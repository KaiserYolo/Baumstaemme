package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {

    public static GameDto toDto(Game game) {
        if (game == null) {
            return null;
        }
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setCreated(game.getCreated());
        gameDto.setStatus(game.getStatus());
        return gameDto;
    }

    public static List<GameDto> createResponseDto(List<Game> games) {
        if (games == null) {
            return null;
        }
        List<GameDto> gameDtoList = new ArrayList<>();
        games.forEach(game -> {
            gameDtoList.add(GameUtil.createResponseDto(game));
        });
        return gameDtoList;
    }
}
