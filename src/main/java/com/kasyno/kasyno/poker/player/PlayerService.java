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

        Optional<Player> playerByUser = getPlayerByUser(user);

        if(!playerByUser.isPresent() && userService.takeTokensFromUser(minTokenAmount, user.getEmail())) {
            Player player = new Player();
            player.setUser(user);
            player.setTokens(minTokenAmount);
            player.setPlayerStatus(PlayerStatus.WAITING);
            player = playerRepository.save(player);
            return player;
        }

        return null;
    }

    public PlayerInfo getPlayerInfo(Player player)
    {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setName(player.getUser().getNickname());
        playerInfo.setTokens(player.getTokens());
        playerInfo.setStatus(player.getPlayerStatus().name());
        return playerInfo;
    }
}
