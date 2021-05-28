package com.kasyno.kasyno.auth;


import com.kasyno.kasyno.security.ApplicationUserRole;
import com.kasyno.kasyno.user.User;
import com.kasyno.kasyno.user.UserRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kasyno.kasyno.security.ApplicationUserRole.*;

@Repository("PSQL")
public class ApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {

        Optional<User> userByNickname = userRepository.findUserByNickname(username);

        Optional<ApplicationUser> applicationUser = Optional.of(
                new ApplicationUser(
                        userByNickname.get().getNickname(),
                        userByNickname.get().getPassword(),
                        ApplicationUserRole.valueOf(userByNickname.get().getRole()).getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return applicationUser;
    }
}
