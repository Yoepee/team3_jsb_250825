package com.back.global.initData;

import com.back.domain.answer.answer.service.AnswerService;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
    private final MemberService memberService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.count() > 0) return;
        memberService.save("user1", "1234", "유저 1");
    }

    @Transactional
    public void work2() {
        if (questionService.count() > 0) return;
        Member member = memberService.findById(1);
        if (member == null) return;
        Question question1 = questionService.write("주제 1", "내용 1", member);
        answerService.write("답변 1", question1, member);
        answerService.write("답변 2", question1, member);
        questionService.write("주제 2", "내용 2", member);
        questionService.write("주제 3", "내용 3", member);
        questionService.write("프록시 관련 어노테이션?", "@Transactional, @OneToMany", member);
        questionService.write("주제", "내용", member);
        questionService.write("제목 1", "내용 1", member);
        questionService.write("제목 2", "내용 2", member);
        questionService.write("제목 3", "내용 3", member);
        questionService.write("제목 4", "내용 4", member);
        questionService.write("임의 제목1", "임의 내용1", member);
        questionService.write("임의 제목2", "임의 내용2", member);
    }
}
