package com.kasyno.kasyno.user;

import com.kasyno.kasyno.email.EmailService;
import com.kasyno.kasyno.registration.EmailValidator;
import com.kasyno.kasyno.registration.token.ConfirmationTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ConfirmationTokenService confirmationTokenService;
    private EmailService emailService;
    private EmailValidator emailValidator;
    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
        this.confirmationTokenService = Mockito.mock(ConfirmationTokenService.class);
        this.emailService = Mockito.mock(EmailService.class);
        this.emailValidator = Mockito.mock(EmailValidator.class);
        this.userService = new UserService(userRepository, passwordEncoder, confirmationTokenService, emailService, emailValidator);

    }

    @Test
    void signUpUser() {
        Mockito.when(emailValidator.test("monia@gmail.com")).thenReturn(true);
        User user = new User();
        user.setEmail("monia@gmail.com");
        user.setNickname("monia");
        String s = userService.signUpUser(user);
        Assertions.assertNotEquals("error", s);
    }

    @Test
    void signUpUser2() {

        User user2 = new User();
        user2.setEmail("marian@gmail.com");
        user2.setNickname("marian");
        user2.setEnabled(true);
        Mockito.when(emailValidator.test("marian@gmail.com")).thenReturn(true);
        Mockito.when(userRepository.findUserByEmail("marian@gmail.com")).thenReturn(Optional.of(user2));

        User user = new User();
        user.setEmail("marian@gmail.com");
        user.setNickname("marian");
        String s = userService.signUpUser(user);
        Assertions.assertEquals("error", s);
    }

    @Test
    void takeTokensFromUser() {

        User user = new User();
        user.setEmail("marian@gmail.com");
        user.setTokens(100L);
        Mockito.when(userRepository.findUserByEmail("marian@gmail.com")).thenReturn(Optional.of(user));
        boolean response = userService.takeTokensFromUser(-100L, "marian@gmail.com");
        Assertions.assertFalse(response);
    }
}