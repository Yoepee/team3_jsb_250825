package com.back.domain.member.member.controller;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;



    @GetMapping("/signin")
    public String signin() {
        return "member/signin";
    }

    @PostMapping("/signin")
    public String login(String username, String password) {
        return memberService.signin(username, password) ? "redirect:/question" : "redirect:/signin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(String username, String password, String nickname) {
        Member member = new Member(username, password, nickname);
        if (memberService.memberExist(username) == null) {
            memberService.save(member);
            return "redirect:/signin";
        }
        return "redirect:/signup";
    }


}
