package com.back.domain.answer.answer.service;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.repository.AnswerRepository;
import com.back.domain.member.member.entity.Member;
import com.back.domain.question.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void modify(Answer answer, String editContent) {
        answer.setContent(editContent);
        answerRepository.save(answer);
    }

    public Answer write(String content, Question question, Member member) {
        Answer answer = new Answer(content, question, member);
        return answerRepository.save(answer);
    }

    public Answer findById(long id) {
        return answerRepository.findById(id).orElseThrow(() -> new RuntimeException("%d번 답변이 존재하지 않습니다.".formatted(id)));
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
