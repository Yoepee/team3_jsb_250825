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

    @GetMapping("/question")
    public String question(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "question/question";
    }

    @GetMapping("/question/{id}")
    public String questionDetail(@PathVariable int id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        model.addAttribute("answers",
                questionService.findAllAnswersByQuestionId(id));
        return "question/questionDetail";
    }

    @GetMapping("/question/write")
    public String questionWrite(Model model) {
        return "question/questionWrite";
    }

    @PostMapping("/question/write")
    public String questionWriteSubmit(@RequestParam String subject,
                                   @RequestParam String content) {
        questionService.write(subject, content);
        return "redirect:/question";
    }

    @PostMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable int id) {
        questionService.deleteById(id);
        return "redirect:/question";
    }

    @GetMapping("/question/update/{id}")
    public String questionUpdate(@PathVariable int id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "question/questionUpdate";
    }

    @PostMapping("/question/update/{id}")
    public String questionUpdateSubmit(@RequestParam String subject,
                                    @RequestParam String content,
                                    @PathVariable int id) {
        questionService.updateById(id, subject, content);
        return "redirect:/question";
    }

    @PostMapping("/question/search")
    public String questionSearch(@RequestParam String keyword, Model model) {
        model.addAttribute("questions", questionService.findQuestionsBySubject(keyword));
        return "question/question";
    }
}
