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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.kasyno.kasyno.poker.GameState.*;
import static com.kasyno.kasyno.poker.player.PlayerStatus.*;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final PlayerService playerService;

    public List<GameIdAndPlayers> getGames() {
        return gameRepository.findAllByOrderById();
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
        Integer acrivePlayers = 0;

        if(byId.isPresent())
        {
            Game game = byId.get();

            if(game.getGameState() == WAITING_FOR_PLAYERS && game.getPlayers() > 1)
            {
                game.setGameState(BIDDING_1);
                if (game.getPlayer1() != null) {
                    game.getPlayer1().setPlayerStatus(PLAYING);
                    acrivePlayers++;
                }
                if (game.getPlayer2() != null) {
                    game.getPlayer2().setPlayerStatus(PLAYING);
                    acrivePlayers++;
                }
                if (game.getPlayer3() != null) {
                    game.getPlayer3().setPlayerStatus(PLAYING);
                    acrivePlayers++;
                };
                if (game.getPlayer4() != null) {
                    game.getPlayer4().setPlayerStatus(PLAYING);
                    acrivePlayers++;
                }
                game.setActivePlayers(acrivePlayers);
                gameRepository.save(game);
            }
        }
    }

    public void call(Long id, Principal principal)
    {
        Optional<Game> byId = gameRepository.findById(id);
        Optional<User> userByEmail = userService.getUserByEmail(principal.getName());

        if(byId.isPresent() && userByEmail.isPresent() && byId.get().getGameState() != WAITING_FOR_PLAYERS) {
            Game game = byId.get();
            User user = userByEmail.get();
            Integer playerTurn = game.getPlayerTurn();

            switch (game.getPlayerTurn()) {
                case 0:
                    if (game.getPlayer1() != null && game.getPlayer1().getPlayerStatus() == PLAYING) {
                        if (game.getPlayer1().getUser() == user && playerTurn == 0) {
                            game.getPlayer1().setTokens(game.getPlayer1().getTokens() - game.getMinCall());
                            game.setJackpot(game.getJackpot() + game.getMinCall());
                            game.getPlayer1().setPlayerStatus(CALL);
                            game.setPlayerTurn(game.getPlayerTurn() + 1);
                        }
                        break;
                    }
                    else
                    {
                        playerTurn++;
                    }
                case 1:
                    if (game.getPlayer2() != null && game.getPlayer2().getPlayerStatus() == PLAYING) {
                        if (game.getPlayer2().getUser() == user && playerTurn == 1) {
                            game.getPlayer2().setTokens(game.getPlayer2().getTokens() - game.getMinCall());
                            game.setJackpot(game.getJackpot() + game.getMinCall());
                            game.getPlayer2().setPlayerStatus(CALL);
                            game.setPlayerTurn(game.getPlayerTurn() + 1);
                        }
                        break;
                    }
                    else
                    {
                        playerTurn++;
                    }
                case 2:
                    if (game.getPlayer3() != null && game.getPlayer3().getPlayerStatus() == PLAYING) {
                        if (game.getPlayer3().getUser() == user && playerTurn == 2) {
                            game.getPlayer3().setTokens(game.getPlayer3().getTokens() - game.getMinCall());
                            game.setJackpot(game.getJackpot() + game.getMinCall());
                            game.getPlayer3().setPlayerStatus(CALL);
                            game.setPlayerTurn(game.getPlayerTurn() + 1);
                        }
                        break;
                    }
                    else
                    {
                        playerTurn++;
                    }
                case 3:
                    if (game.getPlayer4() != null && game.getPlayer4().getPlayerStatus() == PLAYING) {
                        if (game.getPlayer4().getUser() == user && game.getPlayer4().getPlayerStatus() == PLAYING) {
                            game.getPlayer4().setTokens(game.getPlayer4().getTokens() - game.getMinCall());
                            game.setJackpot(game.getJackpot() + game.getMinCall());
                            game.getPlayer4().setPlayerStatus(CALL);
                            game.setPlayerTurn(game.getPlayerTurn() + 1);
                        }
                        break;
                    }
                    else
                    {
                        playerTurn++;
                    }
            }

            game = gameRepository.save(game);
            if(game.getPlayerTurn().equals(game.getActivePlayers()))
            {
                dealCards(game.getId());
            }
            System.out.println("");
        }
    }

    private void dealCards(Long id){

        Optional<Game> byId = gameRepository.findById(id);

        if(byId.isPresent())
        {
            Game game = byId.get();
            List<String> deck = game.getDeck();

                for (int i = 0; i < 5; i++) {

                    if (game.getPlayer1() != null && game.getPlayer1().getPlayerStatus() == CALL) {
                        game.getPlayer1().getDeck().add(deck.remove(0));
                    }
                    if (game.getPlayer2() != null && game.getPlayer2().getPlayerStatus() == CALL) {
                        game.getPlayer2().getDeck().add(deck.remove(0));
                    }
                    if (game.getPlayer3() != null && game.getPlayer3().getPlayerStatus() == CALL) {
                        game.getPlayer3().getDeck().add(deck.remove(0));
                    }
                    if (game.getPlayer4() != null && game.getPlayer4().getPlayerStatus() == CALL) {
                        game.getPlayer4().getDeck().add(deck.remove(0));
                    }
                }
                gameRepository.save(game);
            }
        }
}
