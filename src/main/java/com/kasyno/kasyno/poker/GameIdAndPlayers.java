package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.poker.player.Player;
import com.kasyno.kasyno.user.User;

public interface GameIdAndPlayers {
    String getId();
    Player getPlayer1();
    Player getPlayer2();
    Player getPlayer3();
    Player getPlayer4();

    interface Player {
        User getUser();

        interface User {
            String getNickname();
        }
    }
}
