package com.kasyno.kasyno.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public String registerNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PutMapping(path = "/login/{nickname}")
    public String login(
            @PathVariable("nickname") String nickname,
            @RequestParam String password){

        return userService.login( nickname, password);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String email){

        userService.updateUser(userId, nickname, email);
    }
}
