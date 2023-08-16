package com.example.projectboard.member.ui;

import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.v1.request.CreateMemberRequestDto;
import com.example.projectboard.member.application.dto.v1.request.ModifyMemberRequestDto;
import com.example.projectboard.member.application.dto.v1.response.MemberResponse;
import com.example.projectboard.support.response.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> findMember(@PathVariable Long id) {
        return ApiResponse.success(MemberResponse.from(memberService.findMember(id)));
    }

    @PostMapping
    public ApiResponse<MemberResponse> createMember(@RequestBody CreateMemberRequestDto request) {
        return ApiResponse.success(MemberResponse.from(memberService.createMember(request.toMemberDto())));
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> modifyMember(@PathVariable Long id, @RequestBody ModifyMemberRequestDto request) {
        memberService.modifyMember(id, request.toMemberDto());
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ApiResponse.success();
    }
}
