package com.kasyno.kasyno.auth;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicationUserDaoService implements ApplicationUserDao{

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return Optional.empty();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> getApplicationUsers = new ArrayList<ApplicationUser>(
                new ApplicationUser();
        );

        return getApplicationUsers;
    }
}
