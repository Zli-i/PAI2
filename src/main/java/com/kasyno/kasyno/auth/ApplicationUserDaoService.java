package com.kasyno.kasyno.auth;


import com.kasyno.kasyno.security.ApplicationUserPermission;
import com.kasyno.kasyno.security.ApplicationUserRole;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kasyno.kasyno.security.ApplicationUserRole.*;

@Repository("PG")
public class ApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {

        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }


    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> getApplicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "Anna",
                        passwordEncoder.encode("password"),
                        USER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return getApplicationUsers;
    }
}
