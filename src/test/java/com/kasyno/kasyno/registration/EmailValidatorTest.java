package com.kasyno.kasyno.registration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @Test
    void test1() {
        EmailValidator emailValidator = new EmailValidator();
        String email = new String("marian@gmail.com");
        Assertions.assertTrue(emailValidator.test(email));
        email = "marian";
        Assertions.assertFalse(emailValidator.test(email));
        email = "";
        Assertions.assertFalse(emailValidator.test(email));
        email = null;
        Assertions.assertFalse(emailValidator.test(email));

    }
}