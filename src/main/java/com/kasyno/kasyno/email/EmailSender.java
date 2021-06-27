package com.kasyno.kasyno.email;

import org.springframework.scheduling.annotation.Async;

public interface EmailSender {
    void send(String to, String email);
    void sendToken(String email, String username, String token);
}