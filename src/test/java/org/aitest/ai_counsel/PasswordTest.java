package org.aitest.ai_counsel;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {

    @Test
    public void testPasswordEncoding() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "password";

        // 새로운 해시 생성
        String encoded1 = passwordEncoder.encode(rawPassword);
        String encoded2 = passwordEncoder.encode(rawPassword);

        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encoded 1: " + encoded1);
        System.out.println("Encoded 2: " + encoded2);

        // 기존 data.sql의 해시들 테스트
        String dataSlqHash1 = "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.";
        String dataSlqHash2 = "$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyPw5eee/DFlyMZCIanhjey";
        String dataSlqHash3 = "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG";

        System.out.println("\n=== Testing data.sql hashes ===");
        System.out.println("Hash1 matches 'password': " + passwordEncoder.matches("password", dataSlqHash1));
        System.out.println("Hash2 matches 'password': " + passwordEncoder.matches("password", dataSlqHash2));
        System.out.println("Hash3 matches '123456': " + passwordEncoder.matches("123456", dataSlqHash3));

        // 새로 생성된 해시 테스트
        System.out.println("\n=== Testing new hashes ===");
        System.out.println("Encoded1 matches 'password': " + passwordEncoder.matches(rawPassword, encoded1));
        System.out.println("Encoded2 matches 'password': " + passwordEncoder.matches(rawPassword, encoded2));
    }
}
