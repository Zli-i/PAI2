package com.kasyno.kasyno.Oauth2;


import com.kasyno.kasyno.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oAuth2User;

    @Autowired
    public CustomOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ApplicationUserRole.ADMIN.getGrantedAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("email");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }
}
