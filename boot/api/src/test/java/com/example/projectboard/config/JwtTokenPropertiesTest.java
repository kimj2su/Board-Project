package com.example.projectboard.config;


import com.example.projectboard.acceptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("JwtTokenProperties 테스트")
class JwtTokenPropertiesTest extends AcceptanceTest {

    @Autowired
    JwtTokenProperties jwtTokenProperties;


    @DisplayName("프로파일 프로퍼티 테스트")
    @Test
    void propertiesTest() {
        System.out.println(jwtTokenProperties.getSecretKey());
        System.out.println(jwtTokenProperties.getTokenExpiredTimeMs());
    }

}