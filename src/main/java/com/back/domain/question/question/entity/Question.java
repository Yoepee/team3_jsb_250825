package com.back.domain.question.question.entity;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.member.member.entity.Member;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseEntity {
    private String subject;
    private String content;
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Answer> answerList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    public Question(Member author, String subject, String content) {
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
