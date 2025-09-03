package com.back.domain.question.question.entity;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.member.member.entity.Member;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseEntity {
    @Setter
    private String subject;
    @Setter
    @Column(columnDefinition = "TEXT")
    private String content;
    @OneToMany(mappedBy = "question", cascade = {PERSIST, REMOVE})
    private List<Answer> answerList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
  
    public Question(String subject, String content, Member author) {
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
