package com.example.projectboard.member.application.dto;


import com.example.projectboard.member.domain.Member;
import com.example.projectboard.member.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record MemberDto(Long id, String name, String email, String password, MemberRole memberRole) implements UserDetails {

    public Member toEntity() {
        return new Member(name, email, password);
    }

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getEmail(), member.getPassword(), member.getMemberRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.memberRole().toString()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public MemberRole memberRole() {
        return memberRole;
    }
}
