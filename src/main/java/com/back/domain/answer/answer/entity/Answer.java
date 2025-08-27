package com.back.domain.answer.answer.entity;

import com.back.domain.question.question.entity.Question;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Answer extends BaseEntity {
    @Setter
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public Answer(String content, Question question) {
        this.content = content;
        this.question = question;
    }
}
