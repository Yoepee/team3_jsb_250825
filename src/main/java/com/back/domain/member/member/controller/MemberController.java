package com.back.domain.member.member.controller;

import com.back.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


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
            return "redirect:/signin";
        } catch (Exception e) {
            return "signup";
        }
    }
  
    @GetMapping("/")
    public String redirect() {
        return "member/signin";
    }

    @GetMapping("/signin")
    public String signin() {
        return "member/signin";
    }

    @PostMapping("/signin")
    public String login(String username, String password) {
        return memberService.signin(username, password) ? "redirect:/question" : "redirect:/signin";
    }
}
