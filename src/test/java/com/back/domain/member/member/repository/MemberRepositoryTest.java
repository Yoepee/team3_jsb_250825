package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
        String username = "myid123";
        String password = "password*!12";
        String nickname = "이땡땡";

        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
        member.encodePassword(passwordEncoder);

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getUsername()).isEqualTo(username);
        assertThat(passwordEncoder.matches(password, savedMember.getPassword())).isTrue();
        assertThat(savedMember.getNickname()).isEqualTo(nickname);
    }
}
