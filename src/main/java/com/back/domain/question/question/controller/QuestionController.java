package com.back.domain.question.question.controller;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/create")
    public String showCreate(@ModelAttribute("questionForm") QuestionForm form) {
        return "question/question/question_form";
    }

    @AllArgsConstructor
    @Getter
    public static class QuestionForm {
        @NotBlank(message = "주제를 입력해주세요.")
        @Size(min = 1, max = 20, message = "제목은 1~10 자 이내로 입력해주세요.")
        String subject;

        @NotBlank(message = "내용을 입력해주세요.")
        @Size(min = 2, max = 100, message = "내용은 2~100 자 이내로 입력해주세요.")
        String content;
    }

    @PostMapping("/create")
    @Transactional
    public String create(@ModelAttribute("questionForm") @Valid QuestionForm form,
                         BindingResult bindingResult,
                         Model model) {
        if(bindingResult.hasErrors()) {
            return "question/question/question_form";
        }

        Question question = questionService.create(form.getSubject(), form.getContent());
        model.addAttribute("question", question);

        return "question/question/question_form"; // TODO 질문 상세 보기로 이동
    }
}
