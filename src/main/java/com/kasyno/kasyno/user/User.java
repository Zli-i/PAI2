package com.kasyno.kasyno.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

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
    private LocalDate dob;
    private LocalDate joined;

    @Transient
    private int age;

    public User() {
    }

    public User(Long id, String nickname, String email, LocalDate dob, LocalDate joined) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.dob = dob;
        this.joined = joined;
    }

    public User(String nickname, String email, LocalDate dob, LocalDate joined) {
        this.nickname = nickname;
        this.email = email;
        this.dob = dob;
        this.joined = joined;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", joined=" + joined +
                '}';
    }
}
