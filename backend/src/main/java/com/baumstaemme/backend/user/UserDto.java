package com.baumstaemme.backend.user;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String username;
    private List<Long> playerIds;
}
