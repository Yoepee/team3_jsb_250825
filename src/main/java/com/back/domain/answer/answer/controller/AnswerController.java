package com.back.domain.answer.answer.controller;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;

    @PutMapping("/{id}/edit")
    public Answer modifyAnswer(@PathVariable int id, @RequestBody String editContent) {
        return answerService.modify(id,editContent);
    }
}
