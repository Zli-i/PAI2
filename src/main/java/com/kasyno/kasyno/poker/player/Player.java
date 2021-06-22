package com.kasyno.kasyno.poker.player;

import com.kasyno.kasyno.poker.Game;
import com.kasyno.kasyno.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Player")
public class Player {

    @SequenceGenerator(
            name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "game_id"
    )
    private Game game;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;
    private Long tokens;

}
