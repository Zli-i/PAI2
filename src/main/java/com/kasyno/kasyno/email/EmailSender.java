package com.kasyno.kasyno.email;

public interface EmailSender {
    void send(String to, String email);
}