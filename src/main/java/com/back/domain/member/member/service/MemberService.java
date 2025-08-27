package com.back.domain.member.member.service;

import com.back.domain.member.member.dto.MemberDto;
import com.back.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void register(MemberDto memberDto) {
        if (memberRepository.existsByUsername(memberDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        Member member = new Member();
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword()); // 비밀번호 암호화 필요
        member.setEmail(memberDto.getEmail());
        memberRepository.save(member);
    }
}
