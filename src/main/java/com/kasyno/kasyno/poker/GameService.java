package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.poker.deck.Card;
import com.kasyno.kasyno.poker.deck.Decks;
import com.kasyno.kasyno.poker.deck.Hand;
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
import java.util.*;

import static com.kasyno.kasyno.poker.GameState.*;
import static com.kasyno.kasyno.poker.player.PlayerStatus.*;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

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

    public boolean startGame(Long id) {

        Optional<Game> byId = gameRepository.findById(id);
        Integer acrivePlayers = 0;

        if(byId.isPresent())
        {
            Game game = byId.get();
            List<Player> playerList = game.getPlayerList();
            ListIterator<Player> playerListIterator = playerList.listIterator();


            if(game.getGameState() == WAITING_FOR_PLAYERS && game.getPlayers() > 1)
            {
                game.setGameState(BIDDING_1);

                while (playerListIterator.hasNext()) {
                    playerListIterator.next().setPlayerStatus(PLAYING);
                    acrivePlayers++;
                }

                game.setActivePlayers(acrivePlayers);
                gameRepository.save(game);
                return true;
            }
        }

        return false;
    }

    public void endGame(Long id)
    {
        Optional<Game> byId = gameRepository.findById(id);

        if (!byId.isPresent())
        {
            return;
        }

        Game game = byId.get();
        int[] handValues = {9999,9999,9999,9999};

        if( game.getGameState() == END )
        {
            List<Player> playerList = game.getPlayerList();

            for (int i = 0; i < playerList.size(); i++)
            {
                String cards = new String();
                for (int j = 0; j < playerList.get(i).getDeck().size(); j++){
                    cards += playerList.get(i).getDeck().get(j) + " ";
                }
                Card[] hand = Hand.fromString(cards);

                handValues[i] = Hand.evaluate(hand);
            }

            int minAt = 0;

            for (int i = 0; i < handValues.length; i++) {
                minAt = handValues[i] < handValues[minAt] ? i : minAt;
            }

            userService.addTokensToUser(playerList.get(minAt).getUser().getEmail(), game.getJackpot());

            if(game.getPlayer1() != null)
            {
                Player player = game.getPlayer1();
                userService.addTokensToUser(game.getPlayer1().getUser().getEmail(), game.getPlayer1().getTokens());
                game.setPlayer1(null);
                playerRepository.deleteById(player.getId());
            }
            if(game.getPlayer2() != null)
            {
                Player player = game.getPlayer2();
                userService.addTokensToUser(game.getPlayer2().getUser().getEmail(), game.getPlayer2().getTokens());
                game.setPlayer2(null);
                playerRepository.deleteById(player.getId());
            }
            if(game.getPlayer3() != null)
            {
                Player player = game.getPlayer3();
                userService.addTokensToUser(game.getPlayer3().getUser().getEmail(), game.getPlayer3().getTokens());
                game.setPlayer3(null);
                playerRepository.deleteById(player.getId());
            }
            if(game.getPlayer4() != null)
            {
                Player player = game.getPlayer4();
                userService.addTokensToUser(game.getPlayer4().getUser().getEmail(), game.getPlayer4().getTokens());
                game.setPlayer4(null);
                playerRepository.deleteById(player.getId());
            }

            gameRepository.deleteById(game.getId());
        }
    }

    public void call(Long id, Principal principal)
    {
        Optional<Game> byId = gameRepository.findById(id);
        Optional<User> userByEmail = userService.getUserByEmail(principal.getName());


        if(byId.isPresent() && userByEmail.isPresent() && byId.get().getGameState() != WAITING_FOR_PLAYERS) {
            Game game = byId.get();
            User user = userByEmail.get();
            int playerTurn = game.getPlayerTurn();
            List<Player> playerList = game.getPlayerList();

            for (int i = playerTurn; i < game.getActivePlayers(); i++)
            {
                if (playerList.get(i).getPlayerStatus() == PLAYING) {
                    if (playerList.get(i).getUser() == user) {
                        playerList.get(i).setTokens(playerList.get(i).getTokens() - game.getMinCall());
                        game.setJackpot(game.getJackpot() + game.getMinCall());
                        playerList.get(i).setPlayerStatus(CALL);
                        game.setPlayerTurn(game.getPlayerTurn() + 1);
                    }
                    break;
                }
            }

            game = gameRepository.save(game);
            if(game.getPlayerTurn() == game.getActivePlayers())
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
            List<Player> playerList = game.getPlayerList();

            for (int i = 0; i < 5; i++) {
                ListIterator<Player> playerListIterator = playerList.listIterator();
                while (playerListIterator.hasNext()) {
                    Player next = playerListIterator.next();
                    if (next.getPlayerStatus() == CALL) {
                        next.getDeck().add(deck.remove(0));
                    }
                }
            }
            game.setGameState(END);
            gameRepository.save(game);
        }
    }
}
