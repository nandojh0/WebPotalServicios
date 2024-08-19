/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nando
 */

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncoderTest {

    @Test
    public void testBCryptPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "passwordSeguro123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Imprime la contraseña cifrada para verificar
        System.out.println("Encoded Password: " + encodedPassword);

        // Verifica que la contraseña cifrada coincide con la original
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        assertTrue(matches, "La contraseña cifrada no coincide con la original");
    }
}


