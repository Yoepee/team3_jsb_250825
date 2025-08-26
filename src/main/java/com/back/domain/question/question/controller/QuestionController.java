package com.back.domain.question.question.controller;

import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
}
