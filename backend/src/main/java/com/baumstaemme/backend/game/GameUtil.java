package com.baumstaemme.backend.game;

import com.baumstaemme.backend.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {

    public static GameDto createResponseDto(Game game) {
        if (game == null) {
            return null;
        }
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setCreated(game.getCreated());
        gameDto.setStatus(game.getStatus());
        gameDto.setMapId(game.getMap().getId());

        List<Long> playerIds = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            playerIds.add(player.getId());
        }
        gameDto.setPlayerIds(playerIds);
        return gameDto;
    }

    public static List<GameDto> createResponseDto(List<Game> games) {
        if (games == null) {
            return null;
        }
        List<GameDto> gameDtoList = new ArrayList<>();
        games.forEach(game ->
                gameDtoList.add(createResponseDto(game))
        );
        return gameDtoList;
    }
}
