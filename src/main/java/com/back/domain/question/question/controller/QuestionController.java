package com.back.domain.question.question.controller;

import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/")
    @ResponseBody
    public String list() {
        return "<h1>Main</h1>";
    }

    @GetMapping("/board")
    public String board(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "board/board";
    }

    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable int id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "board/boardDetail";
    }
}
