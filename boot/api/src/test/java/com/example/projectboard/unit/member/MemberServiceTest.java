package com.example.projectboard.unit.member;

import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.member.MemberRole;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.projectboard.support.error.MemberException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Member 서비스 테스트")
public class MemberServiceTest extends AcceptanceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("Member 생성 테스트 - 성공")
    @Test
    void createMember() {
        // given : 선행조건 기술
        MemberDto memberDto = createMemberDto();

        // when : 기능 수행
        MemberDto member = memberService.createMember(memberDto);

        // then : 결과 확인
        assertThat(member.id()).isEqualTo(1L);
    }

    @DisplayName("Member 생성 테스트 - 실패")
    @Test
    void createMemberReturnThrows() {
        // given : 선행조건 기술
        MemberDto createMemberDto = createMemberDto();
        MemberDto savedMember = memberService.createMember(createMemberDto);

        // when : 기능 수행 && then : 결과 확인
        assertThatThrownBy(() -> memberService.createMember(savedMember))
                .isInstanceOf(MemberException.class)
                .hasMessage(savedMember.email());
    }

    @DisplayName("Member 리스트 조회 테스트")
    @Test
    void findAllMembers() {
        // given : 선행조건 기술
        MemberDto memberDto = createMemberDto();
        MemberDto savedMember = memberService.createMember(memberDto);

        // when : 기능 수행
        List<MemberDto> members = memberService.findAllMembers();

        // then : 결과 확인
        assertThat(members).hasSize(1).containsExactly(savedMember);
    }

    @DisplayName("Member 단건 조회 테스트")
    @Test
    void findMember() {
        // given : 선행조건 기술
        MemberDto createMemberDto = createMemberDto();
        MemberDto savedMember = memberService.createMember(createMemberDto);

        // when : 기능 수행
        MemberDto findMember = memberService.findMember(savedMember.id());

        // then : 결과 확인
        assertThat(findMember.id()).isEqualTo(savedMember.id());
        assertThat(findMember.name()).isEqualTo(savedMember.name());
        assertThat(findMember.email()).isEqualTo(savedMember.email());
    }

    @DisplayName("Member 단건 조회 테스트")
    @Test
    void findMemberReturnThrows() {
        // given : 선행조건 기술
        Long notExistMemberId = 1L;

        // when : 기능 수행 & then : 결과 확인
        assertThatThrownBy(() -> memberService.findMember(notExistMemberId))
                .isInstanceOf(MemberException.class)
                .hasMessage(String.format("%s, 회원을 찾을 수 없습니다.", notExistMemberId));
    }

    @DisplayName("Member 수정 테스트")
    @Test
    void modifyMember() {
        // given : 선행조건 기술
        MemberDto createMemberDto = createMemberDto();
        MemberDto savedMember = memberService.createMember(createMemberDto);
        MemberDto modifyMemberDto = modifyMemberDto();

        // when : 기능 수행
        memberService.modifyMember(savedMember.id(), modifyMemberDto);

        // then : 결과 확인
        MemberDto findMember = memberService.findMember(savedMember.id());
        assertThat(findMember.name()).isEqualTo(modifyMemberDto.name());
        assertThat(findMember.email()).isEqualTo(modifyMemberDto.email());
    }

    @DisplayName("Member 삭제 테스트")
    @Test
    void deleteMember() {
        // given : 선행조건 기술
        MemberDto createMemberDto = createMemberDto();
        MemberDto savedMember = memberService.createMember(createMemberDto);

        // when : 기능 수행
        memberService.deleteMember(savedMember.id());

        // then : 결과 확인
        assertThatThrownBy(() -> memberService.findMember(savedMember.id()))
                .isInstanceOf(MemberException.class)
                .hasMessage("1, 회원을 찾을 수 없습니다.");
    }

    @DisplayName("Member 이메일 조회 테스트 - 성공")
    @Test
    void loadMemberByEmail() {
        // given : 선행조건 기술
        MemberDto createMemberDto = createMemberDto();
        MemberDto savedMember = memberService.createMember(createMemberDto);

        // when : 기능 수행
        MemberDto loadMemberByEmail = memberService.loadUserByUsername(savedMember.email());

        // then : 결과 확인
        assertThat(loadMemberByEmail.id()).isEqualTo(savedMember.id());
        assertThat(loadMemberByEmail.name()).isEqualTo(savedMember.name());
        assertThat(loadMemberByEmail.email()).isEqualTo(savedMember.email());
    }

    @DisplayName("Member 이메일 조회 테스트 - 실패")
    @Test
    void loadMemberByEmailReturnThrows() {
        // given : 선행조건 기술

        // when : 기능 수행 && then : 결과 확인
        assertThatThrownBy(() -> memberService.loadUserByUsername("notExistEmail"))
                .isInstanceOf(MemberException.class)
                .hasMessage("notExistEmail not founded");
    }

    private MemberDto createMemberDto() {
        return new MemberDto(null, "김지수", "jisu@email.com", "1234", MemberRole.USER);
    }

    private MemberDto modifyMemberDto() {
        return new MemberDto(null, "김지수2", "jisu2@email.com", "1234", MemberRole.USER);
    }
}
