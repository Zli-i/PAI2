package com.kasyno.kasyno.user;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import com.kasyno.kasyno.security.ApplicationUserRole;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {

        return userRepository.findById( userId );
    }

    public String addNewUser(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());

        if (userByEmail.isPresent() ) {
            //throw new IllegalStateException("email taken");
            return "Email jest już zajęty";
        }
        else {
            userRepository.save(user);
            return "Udało się zarejestrować";
        }
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
}
