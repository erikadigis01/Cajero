package com.Cajero.Cajero;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CajeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CajeroApplication.class, args);
                byte[] key = new byte[64]; // 512 bits
                new SecureRandom().nextBytes(key);
                String secret = Base64.getEncoder().encodeToString(key);
                System.out.println(secret);
                }
}
