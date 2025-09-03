package com.back.domain.answer.answer;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.repository.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class answerTest {
    @Autowired
    private AnswerRepository answerRepository;

//    @Test
//    void zeriTestV1() {
//        AnswerRepository answerRepository = Mockito.mock(AnswerRepository.class);
//        Answer answer = new Answer();
//        answer.modifyContent("original");
//        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
//        Mockito.when(answerRepository.save(answer)).thenReturn(answer);
//
//        AnswerService answerService = new AnswerService(answerRepository);
//
//        Answer result = answerService.modify(1, "edited");
//
//        assertThat(result.getContent()).isEqualTo("edited");
//    }

    @Test
    void zeriTestV2() {
        Answer answer = answerRepository.findById(2L).orElseThrow();
        String oldContent = answer.getContent();

        answer.setContent("수정된 답변 내용2");
        answerRepository.save(answer);

        Answer updated = answerRepository.findById(2L).orElseThrow();
        assertThat(updated.getContent()).isEqualTo("수정된 답변 내용2");
        assertThat(updated.getContent()).isNotEqualTo(oldContent);
    }

}
