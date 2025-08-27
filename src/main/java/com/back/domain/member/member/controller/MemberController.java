package com.back.domain.member.member.controller;

import com.back.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public String register(@ModelAttribute MemberDto memberDto) {
        memberService.register(memberDto);
        return "redirect:/login";
    }
}
