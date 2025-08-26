package com.back.domain.question.question.entity;

import com.back.domain.answer.answer.entity.Answer;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseEntity {
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Answer> answerList = new ArrayList<>();
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;

    public Question(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
