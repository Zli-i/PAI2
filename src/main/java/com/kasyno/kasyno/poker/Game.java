package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.poker.deck.Card;
import com.kasyno.kasyno.poker.player.Player;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Game")
public class Game {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;
    private Long jackpot = 0L;
    private Long minTokenAmount;
    private Long minCall=20L;

    @OneToOne
    @JoinColumn(name = "player1_id")
    private Player player1;
    @OneToOne
    @JoinColumn(name = "player2_id")
    private Player player2;
    @OneToOne
    @JoinColumn(name = "player3_id")
    private Player player3;
    @OneToOne
    @JoinColumn(name = "player4_id")
    private Player player4;

    private Integer playerTurn = 0;
    @Enumerated(EnumType.STRING)
    private GameState gameState = GameState.WAITING_FOR_PLAYERS;
    @ElementCollection
    private List<String > deck = new LinkedList<>();
    @Transient
    private Integer players;
    private Integer activePlayers = 0;


    public Integer getPlayers(){

        Integer players = 0;

        if(player1 != null) players++;
        if(player2 != null) players++;
        if(player3 != null) players++;
        if(player4 != null) players++;

        return players;
    }


}
