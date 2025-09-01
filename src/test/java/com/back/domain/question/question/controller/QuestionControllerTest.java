package com.back.domain.question.question.controller;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Transactional
    @DisplayName("로그인된 사용자가 질문을 등록할 때 작성자 정보가 저장되는지 확인")
    void t1() throws Exception {
        String testUsername = "user1";

        mockMvc.perform(post("/questions/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("subject", "테스트 질문입니다.")
                        .param("content", "이것은 테스트 내용입니다.")
                        .with(user(testUsername))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/questions/detail/*"));

        Question savedQuestion = questionRepository.findFirstByOrderByIdDesc();

        assertThat(savedQuestion.getAuthor().getUsername()).isEqualTo(testUsername);
    }
}
