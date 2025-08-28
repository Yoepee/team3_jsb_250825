package com.back.global.initData;

import com.back.domain.answer.answer.service.AnswerService;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
    private final MemberService memberService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            work0();
            work1();
            work2();
        };
    }

    private void work0() {
        if (memberService.count() > 0) return;
        memberService.save("user1", "1234", "유저 1");
    }

    private void work1() {
        if (questionService.count() > 0) return;
        Member member = memberService.findById(1);
        if (member == null) return;
        Question question1 = questionService.write(member,"주제 1", "내용 1");
        answerService.write("답변 1", question1);
        answerService.write("답변 2", question1);
        questionService.write(member,"주제 2", "내용 2");
        questionService.write(member,"주제 3", "내용 3");
        Question question1 = questionService.write("프록시 관련 어노테이션?", "@Transactional, @OneToMany");
        Question question2 = questionService.write("주제", "내용");
    }
    private void work2() {

        questionService.write("제목 1", "내용 1");
        questionService.write("제목 2", "내용 2");
        questionService.write("제목 3", "내용 3");
        questionService.write("제목 4", "내용 4");
    }
}
