package guru.sfg.brewery.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

class PasswordEncodingTests {

    static final String PASSWORD = "password";

    
    @Test
    void testNoOp() {
    	PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
    	String output = DigestUtils.md5DigestAsHex(PASSWORD.getBytes());
        assertEquals(DigestUtils.md5DigestAsHex("password".getBytes()), output);
        assertNotEquals(output, DigestUtils.md5DigestAsHex("Password".getBytes()));
    }
    
    @Test
    void hashingExample() {
    	String output = DigestUtils.md5DigestAsHex(PASSWORD.getBytes());
        assertEquals(DigestUtils.md5DigestAsHex("password".getBytes()), output);
        assertNotEquals(output, DigestUtils.md5DigestAsHex("Password".getBytes()));
    }
}