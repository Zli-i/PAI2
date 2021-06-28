package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.poker.deck.Decks;
import com.kasyno.kasyno.poker.player.Player;
import com.kasyno.kasyno.poker.player.PlayerRepository;
import com.kasyno.kasyno.poker.player.PlayerService;
import com.kasyno.kasyno.poker.player.PlayerStatus;
import com.kasyno.kasyno.user.User;
import com.kasyno.kasyno.user.UserRepository;
import com.kasyno.kasyno.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
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
            Player player = playerService.createPlayer(user, minTokenAmount);

            if (minTokenAmount > 0 && player != null) {

                Game game = new Game();
                game.setMinTokenAmount(minTokenAmount);
                game.setPlayer1(player);
                game.setDeck(Decks.getStandardDeckS());
                Game save = gameRepository.save(game);

                response.setStatus(HttpServletResponse.SC_OK);
                return save.getId();
            }
        }


        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return -1L;
    }

    public void joinGame(Long gameId, Principal principal, HttpServletResponse response){

        Optional<Game> gameById = gameRepository.findById(gameId);
        User user = userService.getUserByEmail(principal.getName()).get();
        response.setStatus(HttpServletResponse.SC_CONFLICT);

        if(gameById.isPresent())
        {
            Game game = gameById.get();
            Player player = playerService.createPlayer(user, game.getMinTokenAmount());


            if (player != null && game.getPlayers() < 4)
            {
                if(game.getPlayer1() == null){
                    gameRepository.updatePlayer1(game.getId(), player);
                }
                else if(game.getPlayer2() == null){
                    gameRepository.updatePlayer2(game.getId(), player);
                }
                else if(game.getPlayer3() == null){
                    gameRepository.updatePlayer3(game.getId(), player);
                }
                else if(game.getPlayer4() == null){
                    gameRepository.updatePlayer4(game.getId(), player);
                }

                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }

    public GameInfo getGameInfo(Long id)
    {
        Optional<Game> byId = gameRepository.findById(id);

        if(byId.isPresent())
        {
            Game game = byId.get();
            GameInfo gameInfo = new GameInfo();
            gameInfo.setGameState(game.getGameState().name());
            gameInfo.setJackpot(game.getJackpot());
            gameInfo.setPlayers(game.getPlayers());
            gameInfo.setPlayerTurn(game.getPlayerTurn());

            if (game.getPlayer1() != null) gameInfo.setPlayer1(playerService.getPlayerInfo(game.getPlayer1()));
            if (game.getPlayer2() != null) gameInfo.setPlayer2(playerService.getPlayerInfo(game.getPlayer2()));
            if (game.getPlayer3() != null) gameInfo.setPlayer3(playerService.getPlayerInfo(game.getPlayer3()));
            if (game.getPlayer4() != null) gameInfo.setPlayer4(playerService.getPlayerInfo(game.getPlayer4()));

            return gameInfo;
        }

        return null;
    }

    public void startGame(Long id) {

        Optional<Game> byId = gameRepository.findById(id);

        if(byId.isPresent())
        {
            Game game = byId.get();

            if(game.getGameState() == GameState.WAITING_FOR_PLAYERS)
            {
                //gameRepository.updateGameState(game.getId(), GameState.DEAL);
                game.setGameState(GameState.DEAL);
                if (game.getPlayer1() != null) game.getPlayer1().setPlayerStatus(PlayerStatus.PLAYING);
                if (game.getPlayer2() != null) game.getPlayer2().setPlayerStatus(PlayerStatus.PLAYING);
                if (game.getPlayer3() != null) game.getPlayer3().setPlayerStatus(PlayerStatus.PLAYING);
                if (game.getPlayer4() != null) game.getPlayer4().setPlayerStatus(PlayerStatus.PLAYING);
                gameRepository.save(game);
            }
        }
    }
}
