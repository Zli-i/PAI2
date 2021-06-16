package com.kasyno.kasyno.user;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import com.kasyno.kasyno.registration.token.ConfirmationToken;
import com.kasyno.kasyno.registration.token.ConfirmationTokenService;
import com.kasyno.kasyno.security.ApplicationUserRole;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {

        return userRepository.findById( userId );
    }

    public void finalizeTransaction(String name, ItemList itemList){

        List<Item> items = itemList.getItems();
        Long quantity = Long.valueOf(items.get(0).getDescription());

        addTokensToUser(name, quantity);
    }

    public void addTokensToUser(String name, Long sum) {
        Optional<User> userByNickname = userRepository.findUserByNickname(name);

        if (userByNickname.isPresent()) {

            Long tokens = userByNickname.get().getTokens();
            userByNickname.get().setTokens(tokens + sum);

            userRepository.save(userByNickname.get());
        }
    }

    public void addNewOAuth2User(String email, String name) {
        User user = new User();

        user.setEmail(email);
        user.setNickname(name);

        Date input = new Date();
        user.setJoined(input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        user.setAuthProvider(AuthenticationProvider.GOOGLE);
        user.setRole(ApplicationUserRole.USER.name());
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId,
                           String nickname,
                           String email) {
        User user = userRepository.findById(userId).orElseThrow( () -> new IllegalStateException("User with id " + userId + " does nor exist"));

        if (nickname != null && nickname.length() > 0 && !Objects.equals(user.getNickname(), nickname)) {
            user.setNickname(nickname);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {

            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if (userOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }

            user.setEmail(email);
        }
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository
                .findUserByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(45),
                user
        );


        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return userRepository.findUserByEmail(email)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    String.format("user with email %s not found", email)));
    }
}
