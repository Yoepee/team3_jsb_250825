package com.back.domain.answer.answer;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.repository.AnswerRepository;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
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
public class AnswerRepositoryTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("전체 답변 조회")
    void t1() {
        List<Answer> answers = answerRepository.findAll();
        assertThat(answers).hasSize(2);
    }

    @Test
    @DisplayName("단일 답변 조회")
    void t2() {
        Optional<Answer> opAnswer = answerRepository.findById(1L);
        Answer answer = opAnswer.get();
        assertThat(answer.getContent()).isEqualTo("답변 1");
    }

    @Test
    @DisplayName("답변 생성")
    void t3() {
        List<Answer> questions = answerRepository.findAll();
        assertThat(questions).hasSize(2);

        Member member = memberService.findById(1);
        Question question = questionService.findById(1);
        Answer answer = new Answer(member, question, "답변 3");
        answerRepository.save(answer);
        assertThat(answer.getId()).isGreaterThan(0);
        assertThat(answer.getContent()).isEqualTo("답변 3");
        assertThat(answer.getQuestion().getId()).isEqualTo(1);
        assertThat(answer.getAuthor().getId()).isEqualTo(1);

        List<Answer> createdQuestions = answerRepository.findAll();
        assertThat(createdQuestions).hasSize(3);
    }

    @Test
    @DisplayName("답변 삭제")
    void t4() {
        List<Answer> questions = answerRepository.findAll();
        assertThat(questions).hasSize(2);

        Optional<Answer> opAnswer = answerRepository.findById(1L);
        Answer answer = opAnswer.get();
        answerRepository.delete(answer);

        List<Answer> deletedQuestions = answerRepository.findAll();
        assertThat(deletedQuestions).hasSize(1);
    }
}
