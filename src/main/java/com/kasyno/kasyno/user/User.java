package com.kasyno.kasyno.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
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
    @NonNull
    private String nickname;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String role;
    @NonNull
    private LocalDate dob;
    @NonNull
    private LocalDate joined;

    @Transient
    private int age;

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
