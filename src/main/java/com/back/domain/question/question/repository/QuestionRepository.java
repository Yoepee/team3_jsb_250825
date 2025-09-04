package com.back.domain.question.question.repository;

import com.back.domain.question.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySubjectContaining(String subject);

    List<Question> findByContentContaining(String content);


    List<Question> findBySubjectOrContentContaining(String subject, String content);


    List<Question> findBySubjectContainingOrContentContaining(String subject, String content);

    Question findFirstByOrderByIdDesc();

    Page<Question> findBySubjectContainingOrContent(Pageable pageable, String subject, String content);
}
