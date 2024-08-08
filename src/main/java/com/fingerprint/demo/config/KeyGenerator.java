package com.fingerprint.demo.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // Tạo một mảng byte với kích thước 32 (256 bits)
        byte[] keyBytes = new byte[48]; // 256 bits = 32 bytes
        SecureRandom secureRandom = new SecureRandom(); // Tạo một SecureRandom instance
        secureRandom.nextBytes(keyBytes); // Điền vào mảng byte với dữ liệu ngẫu nhiên

        // Tạo SecretKey từ mảng byte
        SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

        // Chuyển đổi khóa thành base64 để lưu trữ
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        // In ra khóa đã mã hóa
        System.out.println("Base64 Encoded Secret Key: " + base64Key);
    }
}
