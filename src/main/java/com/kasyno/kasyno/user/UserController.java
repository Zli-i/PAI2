package com.kasyno.kasyno.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "{userId}")
    public Optional<User> getUser(@PathVariable("userId") Long userId ) {

        return userService.getUser(userId);
    }

    @GetMapping("/logd")
    public UserData getLogdUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userByEmail = userService.getUserByEmail(email);
        if(userByEmail.isPresent())
        {
            User user = userByEmail.get();
            return new UserData(user.getId(), user.getNickname(), user.getEmail(), user.getRole().name(), user.getAuthProvider().name(), user.getJoined(), user.getTokens());
        }
        return null;
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String email){

        userService.updateUser(userId, nickname, email);
    }
}
