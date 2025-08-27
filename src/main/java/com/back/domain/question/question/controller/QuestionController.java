package com.back.domain.question.question.controller;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/search/{kwType}/{kw}")
    public String search(@PathVariable String kwType, @PathVariable String kw, Model model) {
        List<Question> questions = questionService.search(kwType, kw);
        model.addAttribute("questions", questions);
        return "question/question/search"; // resources/templates/question/search.html
    }

    @GetMapping("/search")
    public String searchByParam(@RequestParam String kwType, @RequestParam String kw, Model model) {
        List<Question> questions = questionService.search(kwType, kw);
        model.addAttribute("questions", questions);
        return "question/question/search";
    }
}
