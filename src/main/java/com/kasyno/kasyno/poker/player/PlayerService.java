package com.kasyno.kasyno.poker.player;

import com.kasyno.kasyno.user.User;
import com.kasyno.kasyno.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final UserService userService;

    public Optional<Player> getPlayerByUser(User user){
        return playerRepository.findByUser(user);
    }

    public Player createPlayer(User user, Long minTokenAmount)
    {
        if(userService.takeTokensFromUser(minTokenAmount, user.getId())) {
            Player player = new Player();
            player.setUser(user);
            player.setTokens(minTokenAmount);
            player = playerRepository.save(player);
            return player;
        }
        else
        {
            return null;
        }
    }
}
