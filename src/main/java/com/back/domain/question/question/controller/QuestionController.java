package com.back.domain.question.question.controller;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.model.QuestionForm;
import com.back.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/create")
    public String showCreate(@ModelAttribute("form") QuestionForm form) {
        return "question/question/question_form";
    }

    @PostMapping("/create")
    @Transactional
    public String create(@ModelAttribute("form") @Valid QuestionForm form,
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
