package com.back.domain.member.member.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String password, String nickname) {
        Member member = new Member(username, password, nickname);
        member.encodePassword(passwordEncoder);
        memberRepository.save(member);
    }

    public void save(String username, String password, String nickname) {
        String encodePassword = passwordEncoder.encode(password);
        Member newMember = new Member(username, encodePassword, nickname);
        memberRepository.save(newMember);
    }

    public long count() {
        return memberRepository.count();
    }

    public Member findById(long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    }
}
