package com.back.global.initData;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
    private final QuestionService questionService;

    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            if (questionService.count() > 0) return;

            Question question1 = questionService.save("프록시 관련 어노테이션?", "@Transactional, @OneToMany");
            Question question2 = questionService.save("주제", "내용");
        };
    }

    private void work1() {
        System.out.println("실행 테스트");
    }
}
