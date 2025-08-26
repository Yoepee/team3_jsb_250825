package com.back.global.initData;

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
            if (questionService.count() > 0 ) return;
            work1();
            work2();
        };
    }
    
    private void work1() {
        System.out.println("실행 테스트");
    }
    private void work2() {

        questionService.write("제목 1", "내용 1");
        questionService.write("제목 2", "내용 2");
        questionService.write("제목 3", "내용 3");
        questionService.write("제목 4", "내용 4");
    }
}
