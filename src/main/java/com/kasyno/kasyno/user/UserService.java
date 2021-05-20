package com.kasyno.kasyno.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String login(
                           String nickname,
                           String password) {

        User user = userRepository.findUserByNickname(nickname);

        if (nickname != null && nickname.length() > 0 ) {

            if (user == null){
                return "Nie ma użytkownika o takiej nazwie";
            }
        }

        if (password != null && password.length() > 0 && Objects.equals(user.getPassword(), password)) {

           return "Udało się zalogować";
        }
        else {
            return "Niepoprawne hasło";
        }
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
