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

    public List<Question> search(String kwType, String kw) {
        if("subject".equals(kwType)) {
            return questionRepository.findBySubjectContaining(kw);
        } else if ("content".equals(kwType)) {
            return questionRepository.findByContentContaining(kw);
        } else {
            return questionRepository.findBySubjectOrContentContaining(kw, kw);
        }
    }

}
