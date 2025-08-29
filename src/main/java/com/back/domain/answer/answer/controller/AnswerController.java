package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.dto.DeleteAnswerCommand;
import com.back.domain.answer.answer.service.AnswerService;
import com.back.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/write/{questionId}")
    public String answerWrite(@PathVariable int questionId,
                              @RequestParam String content) {
        answerService.write(content, questionService.findById(questionId));
        return "redirect:/questions/detail/" + questionId;
    }

    @PostMapping("/update/{id}")
    public String modifyAnswer(@PathVariable int id, @RequestParam String content) {
        answerService.modify(id, content);
        return "redirect:/questions/detail/%d".formatted(answerService.findById(id).getQuestion().getId());
    }

   @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable int id,
            @ModelAttribute("form") @Valid DeleteAnswerCommand deleteAnswerCommand,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return "redirect:/questions/detail/%d".formatted(deleteAnswerCommand.getQuestionId());
        }
        answerService.delete(id);
        return "redirect:/questions/detail/%d".formatted(deleteAnswerCommand.getQuestionId());
    }
}
