package com.kasyno.kasyno.Oauth2;

import com.kasyno.kasyno.user.User;
import com.kasyno.kasyno.user.UserRepository;
import com.kasyno.kasyno.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public OAuth2LoginSuccessHandler(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getEmail();
        String name = oAuth2User.getName();

        Optional<User> userByEmail = userRepository.findUserByEmail(email);

        if ( !userByEmail.isPresent() )
        {
            // Register
            userService.addNewOAuth2User(email, name);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
