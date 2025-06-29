package com.baumstaemme.backend.user;

import com.baumstaemme.backend.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    
    public static UserDto createResponseDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        
        List<Long> playerIds = new ArrayList<>();
        List<Player>  players = user.getPlayers();
        for (Player player : players) {
            playerIds.add(player.getId());
        }
        userDto.setPlayerIds(playerIds);
        return userDto;
    }
    
    public static List<UserDto> createResponseDto(List<User> users) {
        if (users == null) {
            return null;
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(createResponseDto(user));
        }
        return userDtos;
    }
}
