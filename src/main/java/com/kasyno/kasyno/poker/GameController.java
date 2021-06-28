package com.kasyno.kasyno.poker;

import com.kasyno.kasyno.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
}
