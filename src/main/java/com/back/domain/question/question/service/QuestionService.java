package com.back.domain.question.question.service;

import com.back.domain.question.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional
    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }

}
