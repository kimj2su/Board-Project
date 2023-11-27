package com.example.projectboard.member.application.dto;


import com.example.projectboard.member.MemberLevel;
import com.example.projectboard.member.Member;
import com.example.projectboard.member.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record MemberDto(Long id, String name, String email, String password, MemberRole memberRole, MemberLevel memberLevel) implements UserDetails {

    public Member toEntity() {
        return Member.of(id, name, email, password);
    }

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getEmail(), member.getPassword(), member.getMemberRole(), member.getLevel().getMemberLevel());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.memberRole().toString()));
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public MemberLevel memberLevel() {
        return memberLevel;
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
    public MemberRole memberRole() {
        return memberRole;
    }
}
