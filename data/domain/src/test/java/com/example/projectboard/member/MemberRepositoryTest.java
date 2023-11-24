package com.example.projectboard.member;

import com.example.projectboard.utils.DomainContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MemberRepository 테스트")
public class MemberRepositoryTest extends DomainContextTest {

    private final String name = "김지수";
    private final String email = "kimjisu3268@gmail.com";
    private final String password = "1234";

    private final MemberRepository memberRepository;

    public MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @DisplayName("회원 저장")
    @Test
    void save() {
        // given
        Member member = createMember(name, email, password);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo(member.getName());
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
    }

    @DisplayName("회원 조회")
    @Test
    void find() {
        // given
        Member savedMember = memberRepository.save(createMember(name, email, password));

        // when
        Optional<Member> findMember = memberRepository.findById(savedMember.getId());

        // then
        assertThat(findMember.get().getId()).isNotNull();
        assertThat(findMember.get().getName()).isEqualTo(savedMember.getName());
        assertThat(findMember.get().getEmail()).isEqualTo(savedMember.getEmail());
    }

    @DisplayName("회원 수정")
    @Test
    @Transactional
    void modify() {
        // given
        Member savedMember = memberRepository.save(createMember(name, email, password));
        String modifyName = "김지수2";
        String modifyEmail = "kimjisu3268@gmail.com2";

        // when
        savedMember.modify(modifyName, modifyEmail);
        Optional<Member> findMember = memberRepository.findById(savedMember.getId());

        // then
        assertThat(findMember.get().getId()).isNotNull();
        assertThat(findMember.get().getName()).isEqualTo(modifyName);
        assertThat(findMember.get().getEmail()).isEqualTo(modifyEmail);
    }

    @DisplayName("회원 삭제")
    @Test
    @Transactional
    void delete() {
        // given
        Member savedMember = memberRepository.save(createMember(name, email, password));

        // when
        memberRepository.delete(savedMember);

        // then
        Optional<Member> findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember.isPresent()).isFalse();
    }

    private Member createMember(String name, String email, String password) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
