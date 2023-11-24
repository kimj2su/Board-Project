package com.example.projectboard.health.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.projectboard.support.response.ApiResponse;

@RestController
@RequestMapping("/health")
public class HealthController {

    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    private final Environment env;

    public HealthController(Environment env) {
        this.env = env;
    }

    @GetMapping
    public ApiResponse<HealthResponse> health() {
        log.info("server port: {}, profile: {}", env.getProperty("local.server.port"), env.getProperty("spring.profiles.active"));
        return ApiResponse.success(new HealthResponse(env.getProperty("local.server.port"), env.getProperty("spring.profiles.active")));
    }
}
