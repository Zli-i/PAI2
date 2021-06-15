package com.kasyno.kasyno.user;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import jdk.jfr.Unsigned;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {

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
    private String role;
    private AuthenticationProvider authProvider;
    private LocalDate dob;
    private LocalDate joined;
    private Long tokens;
    private Boolean locked = false;
    private Boolean enabled = false;


    @Transient
    private int age;

    public User(String nickname, String email, String password, String role, AuthenticationProvider authProvider, LocalDate dob, LocalDate joined, Long toekns) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authProvider = authProvider;
        this.dob = dob;
        this.joined = joined;
        this.tokens = toekns;
    }

}
