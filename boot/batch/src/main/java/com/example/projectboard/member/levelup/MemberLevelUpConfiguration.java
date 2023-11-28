package com.example.projectboard.member.levelup;

import com.example.projectboard.member.Level;
import com.example.projectboard.member.MemberRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MemberLevelUpConfiguration {

    private final String JOB_NAME = "memberLevelUpBatchJob";
    private final int CHUNK_SIZE = 100;
    private final LevelUpJobParameter levelUpJobParameter;
    private final DataSource dataSource;
    private final MemberRepository memberRepository;
    private final TaskExecutor taskExecutor;

    public MemberLevelUpConfiguration(LevelUpJobParameter levelUpJobParameter, DataSource dataSource, MemberRepository memberRepository, TaskExecutor taskExecutor) {
        this.levelUpJobParameter = levelUpJobParameter;
        this.dataSource = dataSource;
        this.memberRepository = memberRepository;
        this.taskExecutor = taskExecutor;
    }

    @Bean(name = JOB_NAME)
    public Job memberLevelUpBatchJob(JobRepository jobRepository, Step memberLevelUpBatchJob_memberLevelUpStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(memberLevelUpBatchJob_memberLevelUpStep)
                .listener(new LevelUpJobExecutionListener())
                .build();
    }

    @Bean(name = JOB_NAME + "_memberLevelUpStep")
    public Step memberLevelUpBatchJob_memberLevelUpStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder(JOB_NAME + "_memberLevelUpStep", jobRepository)
                .<MemberActivityCount, MemberActivityCount>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader())
                .writer(writer())
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean(name = JOB_NAME + "_reader")
    @StepScope
    public ItemReader<MemberActivityCount> reader() {
        Map<String, Object> parameterValues = new HashMap<>();
        LocalDateTime start = levelUpJobParameter.getNowDate();
        LocalDateTime end = levelUpJobParameter.getNowDate().plusDays(1);
        parameterValues.put("start", start);
        parameterValues.put("end", end);

        return new JdbcPagingItemReaderBuilder<MemberActivityCount>()
                .name("memberActivityCountItemReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider())
                .rowMapper(new BeanPropertyRowMapper<>(MemberActivityCount.class))
                .pageSize(CHUNK_SIZE)
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<? super MemberActivityCount> writer() {
        return items -> items.forEach(x -> {
            memberRepository.findById(x.getMemberId())
                    .ifPresent(member -> {
                        Level level = member.getLevel();
                        level.levelUp(x.getPostCount(), x.getLikesCount());
                        memberRepository.save(member);
                    });
        });
    }

    private PagingQueryProvider queryProvider() {
        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setSelectClause(
            """
            m.member_id,
            IFNULL(p.post_count, 0) AS post_count,
            IFNULL(l.likes_count, 0) AS likes_count
            """
        );
        factoryBean.setFromClause(
                """
                FROM
                    member m
                LEFT JOIN (
                    SELECT member_id, COUNT(post_id) AS post_count
                    FROM post
                    WHERE created_at BETWEEN :start AND :end GROUP BY member_id
                ) p ON m.member_id = p.member_id
                LEFT JOIN (
                    SELECT member_id, COUNT(like_id) AS likes_count
                    FROM likes
                    WHERE created_at BETWEEN :start AND :end GROUP BY member_id
                ) l ON m.member_id = l.member_id
                WHERE post_count IS NOT NULL or likes_count IS NOT NULL
                """
        );
        factoryBean.setSortKey("m.member_id");

        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid configuration for query provider", e);
        }
    }
}
