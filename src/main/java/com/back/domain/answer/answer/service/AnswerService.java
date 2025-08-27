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

    public Answer findById(int id){
        return answerRepository.findById(id).orElseThrow(() -> new RuntimeException("질문 불러오기 오류"));
    }

    public Answer write(Member member, Question question, String content) {
        Answer answer = new Answer(member, question, content);
        return answerRepository.save(answer);
    }

    public void delete(int id) {
        Answer answer = findById(id);
        answerRepository.delete(answer);
    }
}
