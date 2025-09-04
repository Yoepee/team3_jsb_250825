package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.dto.AnswerCreateDto;
import com.back.domain.answer.answer.dto.AnswerUpdateDto;
import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.service.AnswerService;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final MemberService memberService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(
            @ModelAttribute("form") AnswerCreateDto form,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/questions/detail/%d".formatted(form.questionId());
        }
        Member member = memberService.findByUsername(principal.getName());
        Question question = questionService.findById(form.questionId());
        Answer answer = answerService.write(form.content(), question, member);
        return "redirect:/questions/detail/%d".formatted(answer.getQuestion().getId());
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("form") AnswerUpdateDto form,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/questions/detail/%d".formatted(form.questionId());
        }
        Answer answer = answerService.findById(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new RuntimeException("수정 권한이 없습니다");
        }
        answerService.modify(answer, form.content());
        return "redirect:/questions/detail/%d".formatted(form.questionId());
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(
            @PathVariable Long id,
            Principal principal
    ) {
        Answer answer = answerService.findById(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new RuntimeException("삭제 권한이 없습니다");
        }
        long questionId = answer.getQuestion().getId();
        answerService.delete(answer);
        return "redirect:/questions/detail/%d".formatted(questionId);
    }
}
