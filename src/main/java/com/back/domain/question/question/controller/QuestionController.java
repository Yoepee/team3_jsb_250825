package com.back.domain.question.question.controller;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/create")
    public String questionCreate() {
        return "question/question/question_form";
    }

    @PostMapping("/create")
    @ResponseBody
    @Transactional
    public String questionCreate(@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content) {
        Question question = questionService.create(subject, content);
        return """
                <div>
                    <span>%d</span>
                    /
                    <span>%s</span>
                    /
                    <span>%s</span>
                </div>
                """.formatted(question.getId(), question.getSubject(), question.getContent()); // TODO 질문 상세 보기로 이동
    }
}
