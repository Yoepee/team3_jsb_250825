package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.service.AnswerService;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(
                        @RequestParam Long questionId,
                        @RequestParam String content
    ) {
        Answer answer = answerService.write(content, questionService.findById(questionId));
        return "redirect:/questions/detail/%d".formatted(answer.getQuestion().getId());
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String update(@PathVariable Long id, @RequestParam String content) {
        Answer answer = answerService.modify(id, content);
        return "redirect:/questions/detail/%d".formatted(answer.getQuestion().getId());
    }

   @PostMapping("/delete/{id}")
   @PreAuthorize("isAuthenticated()")
    public String delete(
            @PathVariable Long id,
            @RequestParam Long questionId
    ) {
        answerService.delete(id);
        return "redirect:/questions/detail/%d".formatted(questionId);
    }
}
