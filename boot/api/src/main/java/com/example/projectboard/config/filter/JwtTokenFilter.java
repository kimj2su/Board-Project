package com.example.projectboard.config.filter;

import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.support.error.ErrorType;
import com.example.projectboard.support.error.MemberException;
import com.example.projectboard.support.jwt.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);
  private static final List<String> TOKEN_IN_PARAM_URLS = List.of("/posts/1/subscribe");
  private final String key;
  private final MemberService memberService;

  public JwtTokenFilter(String key, MemberService memberService) {
    this.key = key;
    this.memberService = memberService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String token;
    try {
      if (TOKEN_IN_PARAM_URLS.contains(request.getRequestURI())) {
        log.info("Request with {} check the quert param", request.getRequestURL());
        token = request.getQueryString().split("=")[1].trim();
      } else {
        //get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
          log.error("Authorization Header does not start with Bearer {} \n",
              request.getRequestURL());
          filterChain.doFilter(request, response);
          return;
        }
        token = header.split(" ")[1].trim();
      }

      if (JwtTokenUtils.isExpired(token, key)) {
        log.error("Key is expired");
        filterChain.doFilter(request, response);
        return;
      }

      String email = JwtTokenUtils.getEmail(token, key);
      MemberDto memberDto = memberService.loadUserByUsername(email);

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          memberDto, null, memberDto.getAuthorities());

      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    } catch (RuntimeException e) {
      throw new MemberException(ErrorType.INVALID_PERMISSION, "Invalid permission");
//            log.error("Error occurs while validating. {}", e.toString());
//            filterChain.doFilter(request, response);
//            return;
    }

    filterChain.doFilter(request, response);
  }
}
