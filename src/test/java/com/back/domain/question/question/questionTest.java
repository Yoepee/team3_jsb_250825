package com.back.domain.question.question;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import com.back.domain.question.question.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
public class questionTest {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
        Question q1 = new Question("테스트 제목1", "테스트 내용1");
        Question q2 = new Question("테스트 제목2", "테스트 내용2");
        questionRepository.save(q1);
        questionRepository.save(q2);
    }
    @Test
    void findWithSubV1() {
        List<Question> result = questionService.search("subject", "제목");
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSubject()).contains("제목1");
    }

    @Test
    void findWithSubV2() {
        List<Question> result = questionService.search("subject", "제목1");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSubject()).contains("제목1");
    }

    @Test
    void findWithSubV3() {
        List<Question> result = questionService.search("subject", "제목");
        System.out.println("제목 검색 결과 개수: " + result.size());
    }
}
