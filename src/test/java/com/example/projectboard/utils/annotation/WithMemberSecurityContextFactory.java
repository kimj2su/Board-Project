package com.example.projectboard.utils.annotation;


import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class WithMemberSecurityContextFactory implements WithSecurityContextFactory<WithMember> {

    @Override
    public SecurityContext createSecurityContext(WithMember withMember) {
        String email = withMember.value();

        Member member = new Member("김지수", email, "1234");

        MemberDto memberDto = new MemberDto(1L, member.getName(), member.getEmail(), member.getPassword(), MemberRole.USER);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        memberDto,
                        null,
                        memberDto.getAuthorities());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }

}
