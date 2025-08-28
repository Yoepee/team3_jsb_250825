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
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final QuestionService questionService;
    private final AnswerService answerService;
  
    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            work0();
            work1();
            work2();
            work3();
            work4();
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
        questionService.write("프록시 관련 어노테이션?", "@Transactional, @OneToMany");
        questionService.write("주제", "내용");
    }
    private void work2() {
        questionService.write("제목 1", "내용 1");
        questionService.write("제목 2", "내용 2");
        questionService.write("제목 3", "내용 3");
        questionService.write("제목 4", "내용 4");
    }

    private void work3() {
        if (memberService.count() > 0) return;

        String password = "123";

        Member member1 = Member.builder()
                .username("admin")
                .password(password)
                .nickname("관리자")
                .build();
        Member member2 = Member.builder()
                .username("test123")
                .password(password)
                .nickname("이작가")
                .build();

        memberService.save(member1);
        memberService.save(member2);
    }
  
    private void work4() {
        questionService.write("임의 제목1", "임의 내용1");
        questionService.write("임의 제목2", "임의 내용2");
        System.out.println("임의 데이터 저장 완료");
    }
}
