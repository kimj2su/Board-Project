package com.example.projectboard.member.levelup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LevelUpJobExecutionListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(LevelUpJobExecutionListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getStepExecutions()
                .forEach(x -> {
                    String stepName = x.getStepName();
                    log.info("회원 등급 업데이트 배치 프로그램 stepName = {}", stepName);
                });

        ZoneId zoneId = ZoneId.of("Asia/Seoul");

        LocalDateTime startTime = jobExecution.getStartTime();
        long startTimeMilliseconds = startTime.atZone(zoneId).toInstant().toEpochMilli();

        //  get job's end time
        LocalDateTime endTime = jobExecution.getEndTime();
        long endTimeMilliseconds = endTime.atZone(zoneId).toInstant().toEpochMilli();

        // get diff between end time and start time
        long time = endTimeMilliseconds - startTimeMilliseconds;

        // log diff time
        log.info("회원 등급 업데이트 배치 프로그램");
        log.info("---------------------------");
        log.info("처리 시간 {}ms", time);
    }
}
