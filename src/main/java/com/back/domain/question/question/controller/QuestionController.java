package com.back.domain.question.question.controller;

import com.back.domain.question.question.entity.Question;
import com.back.domain.question.question.model.QuestionForm;
import com.back.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import java.util.List;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/search/{kwType}/{kw}")
    public String search(@PathVariable String kwType, @PathVariable String kw, Model model) {
        List<Question> questions = questionService.search(kwType, kw);
        model.addAttribute("questions", questions);
        return "question/question/search"; // resources/templates/question/search.html
    }

    @GetMapping("/search")
    public String searchByParam(@RequestParam String kwType, @RequestParam String kw, Model model) {
        List<Question> questions = questionService.search(kwType, kw);
        model.addAttribute("questions", questions);
        return "question/question/search";
    }


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

        return "redirect:/questions";
    }
  
    @Transactional(readOnly = true)
    @GetMapping("/list")
    public String showList(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "question/question/list";
    }

    @PostMapping("/list/delete/{id}")
    public String deleteQuestion(@PathVariable int id) {
        questionService.deleteById(id);
        return "redirect:/list";
    }

    @GetMapping("/question/update/{id}")
    public String questionUpdate(@PathVariable int id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "question/questionUpdate";
    }

    @PostMapping("/question/update/{id}")
    public String questionUpdateSubmit(@RequestParam String subject,
                                    @RequestParam String content,
                                    @PathVariable int id) {
        questionService.updateById(id, subject, content);
        return "redirect:/question";
    }

    @PostMapping("/question/search")
    public String questionSearch(@RequestParam String keyword, Model model) {
        model.addAttribute("questions", questionService.findQuestionsBySubject(keyword));
        return "question/question";
    }

    @GetMapping("/{id}/detail")
    @Transactional(readOnly = true)
    public String showDetail(@PathVariable int id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "question/question/detail";
    }
}
