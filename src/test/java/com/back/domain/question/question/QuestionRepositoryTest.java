package com.back.domain.question.question;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class QuestionRepositoryTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("전체 질문 조회")
    void t1() {
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(3);
    }

    @Test
    @DisplayName("단일 질문 조회")
    void t2() {
        Optional<Question> opQuestion = questionRepository.findById(1L);
        Question question = opQuestion.get();
        assertThat(question.getSubject()).isEqualTo("주제 1");
        assertThat(question.getContent()).isEqualTo("내용 1");
    }

    @Test
    @DisplayName("질문 생성")
    void t3() {
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(3);

        Member member = memberService.findById(1);
        Question question = new Question(member, "주제 4", "내용 4");
        questionRepository.save(question);
        assertThat(question.getId()).isGreaterThan(0);
        assertThat(question.getAuthor().getId()).isEqualTo(1);
        assertThat(question.getSubject()).isEqualTo("주제 4");
        assertThat(question.getContent()).isEqualTo("내용 4");

        List<Question> createdQuestions = questionRepository.findAll();
        assertThat(createdQuestions).hasSize(4);
    }
}
