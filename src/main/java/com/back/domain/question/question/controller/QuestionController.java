package com.back.domain.question.question.controller;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.model.QuestionForm;
import com.back.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/questions")
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
                         @AuthenticationPrincipal UserDetails currentUser) {
        if (bindingResult.hasErrors()) {
            return "question/question/question_form";
        }

        if (currentUser == null) {
            return "redirect:/login";
        }

        Question question = questionService.create(form.getSubject(), form.getContent(), currentUser.getUsername());

        return "redirect:/questions/detail/%d".formatted(question.getId());
    }

    @Transactional(readOnly = true)
    @GetMapping("/list")
    public String showList(Model model,
                           @RequestParam(required = false) String kwType,
                           @RequestParam(required = false) String kw
    ) {
        List<Question> questions;
        if (kwType != null && kw != null && !kw.isEmpty()) {
            questions = questionService.search(kwType, kw);
        } else {
            questions = questionService.findAll();
        }
        model.addAttribute("questions", questions);
        return "question/question/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable int id) {
        questionService.deleteById(id);
        return "redirect:/questions/list";
    }

    @GetMapping("/update/{id}")
    public String questionUpdate(@PathVariable int id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "question/questionUpdate";
    }

    @PostMapping("/update/{id}")
    public String questionUpdateSubmit(@RequestParam String subject,
                                       @RequestParam String content,
                                       @PathVariable int id) {
        questionService.updateById(id, subject, content);
        return "redirect:/questions/list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable int id, Model model) {
        Question question = questionService.incrementViewCount(id);
        model.addAttribute("question", question);

        return "question/question/detail";
    }
}
