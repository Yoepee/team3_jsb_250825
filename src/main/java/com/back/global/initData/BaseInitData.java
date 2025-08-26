package com.back.global.initData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class BaseInitData {
    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            work1();
        };
    }
    
    private void work1() {
        System.out.println("실행 테스트");
    }
}
