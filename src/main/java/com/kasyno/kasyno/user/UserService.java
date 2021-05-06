package com.kasyno.kasyno.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public void addNewUser(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());

        if (userByEmail.isPresent() ) {
            throw new IllegalStateException("email taken");
        }
        else {
            userRepository.save(user);
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
