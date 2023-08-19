package com.example.projectboard.config;

import com.example.projectboard.config.filter.ExceptionHandlerFilter;
import com.example.projectboard.config.filter.JwtTokenFilter;
import com.example.projectboard.member.application.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
public class AuthenticationConfig {

    private final MemberService memberService;

    private final JwtTokenProperties jwtTokenProperties;

    public AuthenticationConfig(MemberService memberService, JwtTokenProperties jwtTokenProperties) {
        this.memberService = memberService;
        this.jwtTokenProperties = jwtTokenProperties;
    }

    // @Value("${jwt.secret-key}")
    // private String key;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/*/users/join", "/api/*/users/login", "/error").permitAll()
                        .requestMatchers("/members/**", "/", "/error").permitAll()
                        .requestMatchers("/", "/home").hasRole("USER")
                        .anyRequest().authenticated()
        )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
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

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
