package com.example.projectboard.member.application;


import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRepository;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.MemberException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberDto createMember(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.email()).ifPresent(member -> {
            throw new MemberException(ErrorType.MEMBER_ALREADY_EXIST_ERROR, ErrorType.MEMBER_ALREADY_EXIST_ERROR.getMessage());
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
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, ErrorType.MEMBER_NOT_FOUND_ERROR.getMessage()));
        return MemberDto.from(member);
    }

    public void modifyMember(Long id, MemberDto memberDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, ErrorType.MEMBER_NOT_FOUND_ERROR.getMessage()));
        member.modify(memberDto.name(), memberDto.email());
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, ErrorType.MEMBER_NOT_FOUND_ERROR.getMessage()));
        member.deleted();
    }

    public MemberDto loadMemberByEmail(String email) {
        return  memberRepository.findByEmail(email).map(MemberDto::from).orElseThrow(() ->
                new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, String.format("%s not founded", email)));
    }

}
