package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.service.AnswerService;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/answer/write/{questionId}")
    public String answerWrite(@PathVariable int questionId,
                              @RequestParam String content) {
        answerService.write(content, questionService.findById(questionId));
        return "redirect:/question/" + questionId;
    }
}
