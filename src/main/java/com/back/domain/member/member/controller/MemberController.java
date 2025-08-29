package com.back.domain.member.member.controller;

import com.back.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
  
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        Object exception = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exception instanceof String) {
            model.addAttribute("errorMessage", exception);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

        return "member/member/login_form";
    }

    @PostMapping("/login")
    public String login() {

        return "redirect:/questions/list";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam (value = "username", required = true) String username,
                         @RequestParam (value = "password", required = true) String password,
                         @RequestParam (value = "nickname", required = true) String nickname) {
        try {
            memberService.create(username, password, nickname);
            return "redirect:/login";
        } catch (Exception e) {
            return "signup";
        }
    }
}
