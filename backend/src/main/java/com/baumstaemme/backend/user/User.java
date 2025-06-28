package com.baumstaemme.backend.user;

import com.baumstaemme.backend.game.player.Player;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank @Size(max = 20)
    private String username;

    @NotBlank @Size(max = 20)
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players;
}
