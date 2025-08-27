package com.back.domain.question.question.service;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question save(String subject, String content) {
        Question question = new Question(subject, content);
        return questionRepository.save(question);
    }

    public long count() {
        return questionRepository.count();
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }
}
