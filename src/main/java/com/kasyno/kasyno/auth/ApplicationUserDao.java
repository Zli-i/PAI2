package com.kasyno.kasyno.auth;

import org.springframework.context.annotation.Bean;

import java.util.Optional;

public interface ApplicationUserDao {
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
