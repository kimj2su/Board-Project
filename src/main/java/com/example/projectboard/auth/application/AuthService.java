package com.example.projectboard.auth.application;

import com.example.projectboard.auth.application.dto.AuthDto;
import com.example.projectboard.config.JwtTokenProperties;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.MemberException;
import com.example.projectboard.support.jwt.JwtTokenUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProperties jwtTokenProperties;

    public AuthService(MemberService memberService, BCryptPasswordEncoder passwordEncoder, JwtTokenProperties jwtTokenProperties) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProperties = jwtTokenProperties;
    }

    @Transactional(readOnly = true)
    public String login(AuthDto authDto) {
        MemberDto memberDto = memberService.loadUserByUsername(authDto.email());

        if (!passwordEncoder.matches(authDto.password(), memberDto.password())) {
            throw new MemberException(ErrorType.MEMBER_PASSWORD_NOT_MATCH_ERROR, "로그인 정보가 일치하지 않습니다.");
        }

        // 토큰생성
        return JwtTokenUtils.generateToken(memberDto.email(), jwtTokenProperties.getSecretKey(), jwtTokenProperties.getTokenExpiredTimeMs());
    }
}
