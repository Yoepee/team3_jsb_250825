package com.back.domain.member.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 등록")
    void t1() {
//        String username = "myid123";
//        String password = "password*!123456";
//        String encodedPassword = passwordEncoder.encode(password);
//        String nickname = "이땡땡";
//
//        Member member = Member.builder()
//                .username(username)
//                .password(encodedPassword)
//                .nickname(nickname)
//                .build();
//        assertThat(member.getId()).isEqualTo(0);
//
//        Member savedMember = memberRepository.save(member);
//
//        assertThat(savedMember.getId()).isGreaterThan(0);
//        assertThat(savedMember.getUsername()).isEqualTo(username);
//        assertThat(passwordEncoder.matches(password, savedMember.getPassword())).isTrue();
//        assertThat(savedMember.getNickname()).isEqualTo(nickname);
    }
}
