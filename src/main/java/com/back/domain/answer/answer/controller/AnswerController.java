package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
}
