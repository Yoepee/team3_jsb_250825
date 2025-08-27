package com.back.global.initData;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
    private final QuestionRepository questionRepository;

    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            work1();
            work2();
        };
    }
    
    private void work1() {
        System.out.println("실행 테스트");
    }
    private void work2() {
        questionRepository.save(new Question("임의 제목1", "임의 내용1"));
        questionRepository.save(new Question("임의 제목2", "임의 내용2"));
        System.out.println("임의 데이터 저장 완료");
    }
}
