package com.back.domain.member.member.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean signin(String username, String password) {
        Member findMember = memberExist(username);
        if (findMember == null) return false;
        else return findMember.getPassword().equals(password);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member memberExist(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }
}
