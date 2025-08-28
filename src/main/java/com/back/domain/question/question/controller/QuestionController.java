package com.back.domain.question.question.controller;

import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/list/delete/{id}")
    public String deleteQuestion(@PathVariable int id) {
        questionService.deleteById(id);
        return "redirect:/list";
    }
}
