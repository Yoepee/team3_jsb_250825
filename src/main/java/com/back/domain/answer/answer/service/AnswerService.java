package com.back.domain.answer.answer.service;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer modify(int answerId, String editContent) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            answer.modifyContent(editContent);
            return answerRepository.save(answer);
        }else {
            throw new RuntimeException(answerId + "번 답변이 존재하지 않습니다.");
        }
    }
}
