package com.back.domain.question.question.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public long count() {
        return questionRepository.count();
    }
    
    public Question findById(int id){
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("질문 불러오기 오류"));
    }

    public Question write(Member member, String subject, String content) {
        Question question = new Question(member, subject, content);
        return questionRepository.save(question);
    }

    public List<Question> findByAll() {
        return questionRepository.findAll();
    }
}
