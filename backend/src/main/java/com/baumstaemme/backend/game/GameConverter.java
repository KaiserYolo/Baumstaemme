package com.baumstaemme.backend.game;

public class GameConverter {

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
}
