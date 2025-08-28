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
        memberRepository.save(member);
    }

    public boolean signin(String username, String password) {
        Member findMember = memberExist(username);
        if (findMember == null) return false;
        else return passwordEncoder.matches(password, findMember.getPassword());
    }

    public void save(String username, String password, String nickname) {
        String encodePassword = passwordEncoder.encode(password);
        Member newMember = new Member(username, encodePassword, nickname);
        memberRepository.save(newMember);
    }

    public Member memberExist(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    public long count() {
        return memberRepository.count();
    }
  
  public Member findById(int i) {
        return memberRepository.findById(i).orElseThrow(() -> new RuntimeException("Member not found"));
  }
  
  public Member save(Member member) {
        member.encodePassword(passwordEncoder);
        return memberRepository.save(member);
  }
}
