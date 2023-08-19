package com.example.projectboard.member.application;


import com.example.projectboard.config.JwtTokenProperties;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRepository;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.MemberException;
import com.example.projectboard.support.jwt.JwtTokenUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProperties jwtTokenProperties;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProperties jwtTokenProperties) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProperties = jwtTokenProperties;
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

    public MemberDto loadUserByName(String email) {
        return  memberRepository.findByEmail(email).map(MemberDto::from).orElseThrow(() ->
                new MemberException(ErrorType.MEMBER_NOT_FOUND_ERROR, String.format("%s no founded", email)));
    }

    public String login(String email, String password) {
        MemberDto memberDto = loadUserByName(email);

        if (passwordEncoder.matches(password, memberDto.password())) {
            throw new MemberException(ErrorType.MEMBER_PASSWORD_NOT_MATCH_ERROR, String.format("로그인 정보가 일치하지 않습니다."));
        }

        // 토큰생성
        return JwtTokenUtils.generateToken(email, jwtTokenProperties.getSecretKey(), jwtTokenProperties.getTokenExpiredTimeMs());
    }
}
