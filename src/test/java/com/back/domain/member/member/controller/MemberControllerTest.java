package com.back.domain.member.member.controller;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setup() {
        Member member = Member.builder()
                .username("test")
                .password("123")
                .nickname("이작가")
                .build();
        memberService.save(member);
    }

    @Test
    @DisplayName("로그인")
    void t1() throws Exception {
        mockMvc.perform(formLogin("/member/login")
                        .user("test")
                        .password("123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

//    @Test
//    @DisplayName("로그아웃")
//    @WithMockUser // 가상의 로그인 사용자 생성
//    void t2() throws Exception {
//        mockMvc.perform(get("/member/logout").with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/")); // 로그아웃 성공 시 홈으로 리다이렉션 되는지 확인
//    }
}
