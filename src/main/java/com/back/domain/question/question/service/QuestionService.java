package com.back.domain.question.question.service;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question write(String subject, String content) {
        Question question = new Question(subject, content);
        questionRepository.save(question);
        return question;
    }

    public long count() {
        return questionRepository.count();
    }

    public Question findById(int id) {
        return questionRepository.findById(id).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Question not found"));
    }

    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }

    public Question updateById(int id, String subject, String content) {
        Question updatedQuestion = findById(id);
        updatedQuestion.setSubject(subject);
        updatedQuestion.setContent(content);
        questionRepository.save(updatedQuestion);
        return updatedQuestion;
    }

    public List<Answer> findAllAnswersByQuestionId(int questionId) {
        return findById(questionId).getAnswerList();
    }

    public List<Question> findQuestionsBySubject(String subject) {
        return questionRepository.findBySubjectContaining(subject);
    }
}
