package com.example.projectboard.config;


import com.example.projectboard.acceptance.AcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JwtTokenPropertiesTest extends AcceptanceTest {

    @Autowired
    JwtTokenProperties jwtTokenProperties;


    @Test
    void propertiesTest() {
        System.out.println(jwtTokenProperties.getSecretKey());
        System.out.println(jwtTokenProperties.getTokenExpiredTimeMs());
    }

}