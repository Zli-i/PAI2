package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/games")
public class GameController {

    GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getUsers() {
        return gameService.getGames();
    }

    @PostMapping("/create")
    public Long createGame(@RequestParam("minTokenAmount") Long minTokenAmount, HttpServletResponse response)
    {
        return gameService.createGame(minTokenAmount, response);
    }
    @PostMapping("/join")
    public void joinGame(@RequestParam("Id") Long Id, Principal principal, HttpServletResponse response){
        gameService.joinGame(Id, principal, response);
    }
    @PostMapping("/info")
    public GameInfo getGameInfo(@RequestParam("Id") Long Id)
    {
        return gameService.getGameInfo(Id);
    }
    @PostMapping("/start")
    public void startGame(@RequestParam("Id") Long Id)
    {
        gameService.startGame(Id);
    }
}
