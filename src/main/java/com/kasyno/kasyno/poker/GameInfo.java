package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.poker.player.PlayerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameInfo {

    private Long jackpot;
    private String gameState;
    private Integer players;
    private PlayerInfo player1;
    private PlayerInfo player2;
    private PlayerInfo player3;
    private PlayerInfo player4;

}
