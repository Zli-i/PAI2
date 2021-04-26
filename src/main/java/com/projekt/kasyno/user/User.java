package com.projekt.kasyno.user;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public void setJoined(LocalDate joined) {
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

    public int getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }
}
