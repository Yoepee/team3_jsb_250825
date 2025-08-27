package com.back.domain.member.member.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;

    public Member(String username, String rawPassword, String nickname) {
        this.username = username;
        this.password = rawPassword;
        this.nickname = nickname;
    }

    private String encryptPassword(String password) {
        String encryptedPassword = String.valueOf((password+"secret24").hashCode());
        return Base64.getEncoder().encodeToString((password+"salt").getBytes());
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
