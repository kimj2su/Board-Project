package com.example.projectboard.config;

import com.example.projectboard.config.filter.ExceptionHandlerFilter;
import com.example.projectboard.config.filter.JwtTokenFilter;
import com.example.projectboard.member.application.MemberService;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    private final MemberService memberService;

    private final JwtTokenProperties jwtTokenProperties;

    public AuthenticationConfig(MemberService memberService, JwtTokenProperties jwtTokenProperties) {
        this.memberService = memberService;
        this.jwtTokenProperties = jwtTokenProperties;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher( "/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher( "/docs/**"))
                .requestMatchers(new AntPathRequestMatcher( "/auth/login"))
                .requestMatchers(new AntPathRequestMatcher( "/members/**"))
                .requestMatchers(new AntPathRequestMatcher( "/error"))
                .requestMatchers(new AntPathRequestMatcher( "/health"))
                .requestMatchers(new AntPathRequestMatcher( "/static/**"))
                .requestMatchers(new AntPathRequestMatcher( "/index.html"))
                .requestMatchers(new AntPathRequestMatcher( "/"))
                .requestMatchers(new AntPathRequestMatcher( "/management/**"))
                ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .requestMatchers("/posts/**").hasAuthority("USER")
                        .anyRequest().authenticated()
        )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(x -> x.configurationSource(corsConfigurationSource()))
                .addFilterBefore(new JwtTokenFilter(jwtTokenProperties.getSecretKey(), memberService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtTokenFilter.class)
        ;

        return http.build();
    }

    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
