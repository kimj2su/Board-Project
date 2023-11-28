package com.example.projectboard.member.levelup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@StepScope
public class LevelUpJobParameter {

    private static final Logger log = LoggerFactory.getLogger(LevelUpJobParameter.class);
    private LocalDateTime nowDate;

    @Value("#{jobParameters[nowDate]}")
    public void setNowDate(String nowDate) {
        log.info("nowDate = {}", nowDate);
        this.nowDate = LocalDate.parse(nowDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public LocalDateTime getNowDate() {
        return nowDate;
    }
}
