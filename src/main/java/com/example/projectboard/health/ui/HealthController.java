package com.example.projectboard.health.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthController {

    private final Environment env;

    public HealthController(Environment env) {
        this.env = env;
    }

    @GetMapping
    public String health() {
        log.info("server port: {}, profile: {}", env.getProperty("local.server.port"), env.getProperty("spring.profiles.active"));
        return "ok";
    }
}
