package com.back.domain.question.question.controller;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.question.question.dto.QuestionDto;
import com.back.domain.question.question.dto.QuestionSearchDto;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/questions")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    @GetMapping("/list")
    public String showList(
            @ModelAttribute("search") QuestionSearchDto search,
            Model model
    ) {
        List<Question> questions;
        if (search.getKw().equals("all") && search.getKwType().isEmpty()) {
            questions = questionService.findAll();
        } else {
            questions = questionService.search(search.getKwType(), search.getKw());
        }
        model.addAttribute("questions", questions);
        return "question/question/list";
    }

    @GetMapping("/detail/{id}")
    @Transactional(readOnly = true)
    public String showDetail(@PathVariable int id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "question/question/detail";
    }

    @GetMapping("/create")
    public String showCreate(@ModelAttribute("form") QuestionDto form) {
        return "question/question/write";
    }

    @PostMapping("/create")
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public String create(
            @ModelAttribute("form") @Valid QuestionDto form,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question/question/write";
        }
        Member member = memberService.findByUsername(principal.getName());
        Question question = questionService.create(form.getSubject(), form.getContent(), member);

        return "redirect:/questions/detail/%d".formatted(question.getId());
    }

    @GetMapping("/update/{id}")
    public String showUpdate(
            @PathVariable int id,
            @ModelAttribute("form") QuestionDto form,
            Principal principal,
            Model model
    ) {
        Question question = questionService.findById(id);
        String username = principal.getName();
        if (!question.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("수정 권한이 없습니다");
        }
        form.setSubject(question.getSubject());
        form.setContent(question.getContent());
        model.addAttribute("question", question);
        return "question/question/update";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String update(
            @PathVariable int id,
            @ModelAttribute("form") @Valid QuestionDto form,
            BindingResult bindingResult,
            Principal principal
    ) {
        if(bindingResult.hasErrors()){
            return "question/question/update";
        }
        Question question = questionService.findById(id);
        String username = principal.getName();
        if (!question.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("수정 권한이 없습니다");
        }
        questionService.update(question, form.getSubject(), form.getContent());

        return "redirect:/questions/detail/%d".formatted(question.getId());
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(
            @PathVariable int id,
            Principal principal
    ) {
        Question question = questionService.findById(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new RuntimeException("삭제 권한이 없습니다");
        }
        questionService.delete(question);
        return "redirect:/questions/list";
    }
}
