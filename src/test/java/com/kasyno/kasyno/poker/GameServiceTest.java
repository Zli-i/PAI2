package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.email.EmailService;
import com.kasyno.kasyno.poker.player.Player;
import com.kasyno.kasyno.poker.player.PlayerRepository;
import com.kasyno.kasyno.poker.player.PlayerService;
import com.kasyno.kasyno.registration.EmailValidator;
import com.kasyno.kasyno.registration.token.ConfirmationTokenService;
import com.kasyno.kasyno.user.UserRepository;
import com.kasyno.kasyno.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private  GameRepository gameRepository;
    private  UserService userService;
    private  PlayerService playerService;
    private  PlayerRepository playerRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        this.gameRepository = Mockito.mock(GameRepository.class);
        this.userService = Mockito.mock(UserService.class);
        this.playerService = Mockito.mock(PlayerService.class);
        this.playerRepository = Mockito.mock(PlayerRepository.class);
        this.gameService = new GameService(gameRepository, userService, playerService, playerRepository);

    }

    @Test
    void createGame() {

        boolean response = gameService.startGame(1L);
        Assertions.assertFalse(response);
    }

    @Test
    void createGame2() {

        Game game = new Game();
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        boolean response = gameService.startGame(1L);
        Assertions.assertFalse(response);
    }

    @Test
    void createGame3() {

        Game game = new Game();
        game.setGameState(GameState.WAITING_FOR_PLAYERS);
        game.setPlayer1(new Player());
        game.setPlayer2(new Player());
        game.setPlayer3(new Player());
        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        boolean response = gameService.startGame(1L);
        Assertions.assertTrue(response);
    }
}