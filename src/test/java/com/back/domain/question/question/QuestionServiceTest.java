package com.back.domain.question.question;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("전체 질문 조회")
    void t1() {
        List<Question> questions = questionService.findAll();

        assertThat(questions).hasSize(2);
    }

//    @Test
//    @DisplayName("질문 저장")
//    void t2() {
//        Question question = questionService.save("제목", "내용");
//        assertThat(question.getSubject()).isEqualTo("제목");
//        assertThat(question.getContent()).isEqualTo("내용");
//    }

    @Test
    @DisplayName("질문 개수")
    void t3() {
        long count = questionService.count();
        assertThat(count).isEqualTo(2);
    }

}
