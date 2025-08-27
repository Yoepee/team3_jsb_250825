package com.back.global.initData;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            work1();
            work2();
        };
    }

    private void work1() {
        System.out.println("실행 테스트");
    }

    private void work2() {
        if (memberService.count() > 0) return;

        String password = "123";

        Member member1 = Member.builder()
                .username("admin")
                .password(passwordEncoder.encode(password))
                .nickname("관리자")
                .build();
        Member member2 = Member.builder()
                .username("test123")
                .password(passwordEncoder.encode(password))
                .nickname("이작가")
                .build();

        memberService.save(member1);
        memberService.save(member2);
    }
}
