package com.example.projectboard.member.application;


import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRepository;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto createMember(MemberDto memberDto) {
        return MemberDto.from(memberRepository.save(memberDto.toEntity()));
    }

    @Transactional(readOnly = true)
    public MemberDto findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, id));
        return MemberDto.from(member);
    }

    public void modifyMember(Long id, MemberDto memberDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, id));
        member.modify(memberDto.name(), memberDto.email());
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, id));
        member.deleted();
    }
}
