package com.back.domain.answer.answer.entity;

import com.back.domain.member.member.entity.Member;
import com.back.domain.question.question.entity.Question;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Answer extends BaseEntity {
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    public Answer(Member author, Question question, String content){
        this.author = author;
        this.question = question;
        this.content = content;
    }
}
