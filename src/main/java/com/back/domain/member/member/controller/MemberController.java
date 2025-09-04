package com.back.domain.member.member.controller;

import com.back.domain.member.member.dto.SignupDto;
import com.back.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
  
    @GetMapping("/login")
    public String showLogin(HttpSession session, Model model) {
        Object exception = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exception instanceof String) {
            model.addAttribute("errorMessage", exception);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

        return "member/member/login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("form") SignupDto form) {
        return "member/member/signup";
    }

    @PostMapping("/signup")
    public String signup(
            @ModelAttribute("form") @Valid SignupDto form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "member/member/signup";
        }

        if(!form.password().equals(form.password_confirm())) {
            bindingResult.rejectValue("password", "checkPassword", "비밀번호가 일치하지 않습니다.");
            return "member/member/signup";
        }

        boolean hasMember = memberService.hasMemberByUsername(form.username());
        if(hasMember) {
            bindingResult.rejectValue("username", "checkUsername", "계정이 존재합니다.");
            return "member/member/signup";
        }

        try{
            memberService.create(form.username(), form.password(), form.nickname());
        } catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("registerError", e.getMessage());
            return "member/member/signup";
        }

        return "redirect:login";
    }
}
