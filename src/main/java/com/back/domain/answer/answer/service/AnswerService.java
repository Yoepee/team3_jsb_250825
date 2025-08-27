package com.back.domain.answer.answer.service;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.repository.AnswerRepository;
import com.back.domain.question.question.entity.Question;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer write(String content, Question question) {
        Answer answer = new Answer(content, question);
        answerRepository.save(answer);
        return answer;
    }

    public void update(String content, int id) {
        Answer answer = answerRepository.findById(id).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Answer not found"));
        answer.setContent(content);
        answerRepository.save(answer);
    }
}
