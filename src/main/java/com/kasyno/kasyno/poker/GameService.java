package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.poker.player.Player;
import com.kasyno.kasyno.poker.player.PlayerRepository;
import com.kasyno.kasyno.poker.player.PlayerService;
import com.kasyno.kasyno.user.User;
import com.kasyno.kasyno.user.UserRepository;
import com.kasyno.kasyno.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final PlayerService playerService;

    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    public Long createGame(Long minTokenAmount, HttpServletResponse response) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userByEmail = userService.getUserByEmail(email);

        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            Optional<Player> playerByUser = playerService.getPlayerByUser(user);

            if (minTokenAmount > 0 && !playerByUser.isPresent()) {
                Player player = playerService.createPlayer(user, minTokenAmount);

                if(player != null) {

                    Game game = new Game();
                    game.setMinTokenAmount(minTokenAmount);
                    game.setPlayer1(player);
                    Game save = gameRepository.save(game);

                    response.setStatus(HttpServletResponse.SC_OK);
                    return save.getId();
                }
            }
        }


        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return -1L;
    }
}
