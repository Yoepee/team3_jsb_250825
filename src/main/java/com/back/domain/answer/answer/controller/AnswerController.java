package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.dto.DeleteAnswerCommand;
import com.back.domain.answer.answer.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable int id,
            @ModelAttribute("form") @Valid DeleteAnswerCommand deleteAnswerCommand,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return "redirect:/questions/%d/detail".formatted(deleteAnswerCommand.getQuestionId());
        }
        answerService.delete(id);
        return "redirect:/questions/%d/detail".formatted(deleteAnswerCommand.getQuestionId());
    }
}
