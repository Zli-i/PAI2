package com.kasyno.kasyno.user;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import com.kasyno.kasyno.security.ApplicationUserRole;
import jdk.jfr.Unsigned;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails {

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", authProvider=" + authProvider +
                ", dob=" + dob +
                ", joined=" + joined +
                ", age=" + age +
                '}';
    }

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String nickname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;
    private LocalDate dob;
    private LocalDate joined;
    private Long tokens;
    private Boolean locked = false;
    private Boolean enabled = false;


    @Transient
    private int age;

    public User(String nickname, String email, String password, ApplicationUserRole role, AuthenticationProvider authProvider, LocalDate dob, LocalDate joined, Long toekns) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authProvider = authProvider;
        this.dob = dob;
        this.joined = joined;
        this.tokens = toekns;
    }

    public User(String nickname, String email, String password, ApplicationUserRole role, AuthenticationProvider authProvider, LocalDate dob, LocalDate joined, Long toekns, Boolean enabled) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authProvider = authProvider;
        this.dob = dob;
        this.joined = joined;
        this.tokens = toekns;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
