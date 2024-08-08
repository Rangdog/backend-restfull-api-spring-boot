package com.fingerprint.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret; // Khóa được mã hóa base64

    // Khai báo thời gian hết hạn
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 giờ

    // Trả về khóa bảo mật từ base64
    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    // Tạo token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()) // Thời gian phát hành
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Thời gian hết hạn
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // Sử dụng khóa an toàn
                .compact();
    }

    // Kiểm tra tính hợp lệ của token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Lấy username từ token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Lấy tất cả thông tin từ token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey()) // Sử dụng khóa an toàn
                .parseClaimsJws(token)
                .getBody();
    }

    // Kiểm tra xem token đã hết hạn hay chưa
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}