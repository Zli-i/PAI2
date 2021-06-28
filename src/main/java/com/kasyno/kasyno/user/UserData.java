package com.kasyno.kasyno.user;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import com.kasyno.kasyno.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserData {
    private Long id;
    private String nickname;
    private String email;
    private String role;
    private String authProvider;
    private LocalDate joined;
    private Long tokens;
}
