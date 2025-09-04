package com.back.domain.question.question.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.repository.MemberRepository;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    public Question create(String subject, String content, Member author) {
        Question question = new Question(subject, content, author);
        return questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public long count() {
        return questionRepository.count();
    }

    public Question findById(long id) {
        return questionRepository.findById(id).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Question not found"));
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public Question update(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        return questionRepository.save(question);
    }

    public Question write(String subject, String content, Member member) {
        Question question = new Question(subject, content, member);
        return questionRepository.save(question);
    }

    public List<Question> search(String kwType, String kw) {
        if ("subject".equals(kwType)) return questionRepository.findBySubjectContaining(kw);
        if ("content".equals(kwType)) return questionRepository.findByContentContaining(kw);

        return questionRepository.findBySubjectContainingOrContentContaining(kw, kw);
    }

    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Page<Question> search(String kwType, String kw, Pageable pageable) {
        if ("subject".equals(kwType)) {
            return questionRepository.findBySubjectContainingOrderByIdDesc(kw, pageable);
        } else if ("content".equals(kwType)) {
            return questionRepository.findByContentContainingOrderByIdDesc(kw, pageable);
        } else {
            return questionRepository.findBySubjectContainingOrContentContainingOrderByIdDesc(kw, kw, pageable);
        }
    }
}
