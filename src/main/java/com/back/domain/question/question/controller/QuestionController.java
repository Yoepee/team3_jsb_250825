package com.back.domain.question.question.controller;

import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        return "board/boardWrite";
    }

    @PostMapping("/board/write")
    public String boardWriteSubmit(@RequestParam String subject,
                                   @RequestParam String content) {
        questionService.write(subject, content);
        return "redirect:/board";
    }
}
