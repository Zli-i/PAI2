package com.kasyno.kasyno.user;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import com.kasyno.kasyno.email.EmailService;
import com.kasyno.kasyno.registration.token.ConfirmationToken;
import com.kasyno.kasyno.registration.token.ConfirmationTokenService;
import com.kasyno.kasyno.security.ApplicationUserRole;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {

        return userRepository.findById( userId );
    }

    public Optional<User> getUserByEmail(String email) {

        return userRepository.findUserByEmail(email);
    }

    public boolean takeTokensFromUser(Long tokens, Long userId){

        User one = userRepository.getOne(userId);
        Long userTokens = one.getTokens();
        if(userTokens >= tokens && tokens > 0)
        {
            one.setTokens(userTokens-tokens);
            userRepository.save(one);
            return true;
        }
        return false;
    }

    public void finalizeTransaction(String email, ItemList itemList){

        List<Item> items = itemList.getItems();
        Long quantity = Long.valueOf(items.get(0).getDescription());

        addTokensToUser(email, quantity);
    }

    public void addTokensToUser(String email, Long sum) {
        Optional<User> userByNickname = userRepository.findUserByEmail(email);

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
        user.setRole(ApplicationUserRole.USER);
        user.setTokens(1000L);
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

        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());

        boolean userExists = userByEmail.isPresent();

        if (userExists) {

            if (!userByEmail.get().getEnabled() )
            {
                Optional<ConfirmationToken> token = confirmationTokenService.getTokenByUser(userByEmail.get());
                emailService.sendToken(userByEmail.get().getEmail(), userByEmail.get().getUsername(), token.get().getToken());
            }

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
