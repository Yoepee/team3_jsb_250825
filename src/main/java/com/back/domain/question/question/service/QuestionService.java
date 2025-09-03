package com.back.domain.question.question.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public Question create(String subject, String content, String username) {
        Member author = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        Question question = new Question(author, subject, content);
        return questionRepository.save(question);
    }

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

    public Question findById(long id) {
        return questionRepository.findById(id).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Question not found"));
    }

    public void deleteById(long id) {
        questionRepository.deleteById(id);
    }

    public Question updateById(long id, String subject, String content) {
        Question updatedQuestion = findById(id);
        updatedQuestion.setSubject(subject);
        updatedQuestion.setContent(content);
        questionRepository.save(updatedQuestion);
        return updatedQuestion;
    }

    public Question write(Member member, String subject, String content) {
        Question question = new Question(member, subject, content);
        return questionRepository.save(question);
    }

    public List<Question> search(String kwType, String kw) {
        if ("subject".equals(kwType)) return questionRepository.findBySubjectContaining(kw);
        if ("content".equals(kwType)) return questionRepository.findByContentContaining(kw);

        return questionRepository.findBySubjectContainingOrContentContaining(kw, kw);
    }
}
