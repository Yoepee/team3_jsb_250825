package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.dto.DeleteAnswerCommand;
import com.back.domain.answer.answer.service.AnswerService;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/answer/write/{questionId}")
    public String answerWrite(@PathVariable int questionId,
                              @RequestParam String content) {
        answerService.write(content, questionService.findById(questionId));
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/answer/update/{answerId}") // url에서 바로 넘겨주도록 테스트하기 위한 용도로 GET 설정
//    @PostMapping("/answer/update/{answerId}")
    public String answerUpdate(@PathVariable int answerId,
                               @RequestParam String content) {
        answerService.update(content, answerId);
        return "redirect:/question";
    }

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
