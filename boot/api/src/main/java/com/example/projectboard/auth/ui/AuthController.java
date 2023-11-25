package com.example.projectboard.auth.ui;

import com.example.projectboard.auth.application.AuthService;
import com.example.projectboard.auth.application.dto.request.AuthRequest;
import com.example.projectboard.auth.application.dto.response.AuthResponse;
import com.example.projectboard.auth.application.dto.response.MyPageResponse;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.support.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        String token = authService.login(request.toDto());
        return ApiResponse.success(new AuthResponse(token));
    }

    @GetMapping("/myPage")
    public ApiResponse<MyPageResponse> myPage(@AuthenticationPrincipal MemberDto memberDto) {
        return ApiResponse.success(MyPageResponse.from(authService.findMember(memberDto.id())));
    }
}
