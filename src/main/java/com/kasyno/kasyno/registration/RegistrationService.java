package com.kasyno.kasyno.registration;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import com.kasyno.kasyno.email.EmailSender;
import com.kasyno.kasyno.email.EmailService;
import com.kasyno.kasyno.registration.token.ConfirmationToken;
import com.kasyno.kasyno.registration.token.ConfirmationTokenService;
import com.kasyno.kasyno.security.ApplicationUserRole;
import com.kasyno.kasyno.user.User;
import com.kasyno.kasyno.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static com.kasyno.kasyno.Oauth2.AuthenticationProvider.*;
import static com.kasyno.kasyno.security.ApplicationUserRole.*;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = userService.signUpUser(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        USER,
                        LOCAL,
                        LocalDate.of(1998, Month.JANUARY, 5),
                        LocalDate.now(),
                        (long)1000
                )
        );

        emailService.sendToken(request.getEmail(), request.getUsername(), token);

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }
}
