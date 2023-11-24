package com.example.projectboard.member.application;


import com.example.projectboard.member.Member;
import com.example.projectboard.member.MemberRepository;
import com.example.projectboard.member.application.dto.MemberDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.MemberException;

import java.util.List;

@Service
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberDto createMember(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.email()).ifPresent(member -> {
            throw new MemberException(ErrorType.MEMBER_ALREADY_EXIST_ERROR, memberDto.email());
        });
        Member member = memberDto.toEntity();
        member.encryptPassword(passwordEncoder.encode(memberDto.password()));
        return MemberDto.from(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public List<MemberDto> findAllMembers() {
        return memberRepository.findAll().stream().map(MemberDto::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberDto findMember(Long id) {
        Member member = getMember(id);
        return MemberDto.from(member);
    }

    public void modifyMember(Long id, MemberDto memberDto) {
        Member member = getMember(id);
        member.modify(memberDto.name(), memberDto.email());
    }

    public void deleteMember(Long id) {
        Member member = getMember(id);
        member.deleted();
    }

    @Override
    public MemberDto loadUserByUsername(String email) {
        return  memberRepository.findByEmail(email).map(MemberDto::from).orElseThrow(() ->
                new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, String.format("%s not founded", email)));
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, String.format("%s, 회원을 찾을 수 없습니다.", id)));
    }
}
