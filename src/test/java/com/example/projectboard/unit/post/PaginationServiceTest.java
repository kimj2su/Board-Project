package com.example.projectboard.unit.post;

import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.post.appllication.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pagination 서비스 테스트")
public class PaginationServiceTest extends AcceptanceTest {

    @Autowired
    private PaginationService paginationService;

    @DisplayName("페이지네이션 바 숫자 리스트 생성")
    @Test
    void getPaginationBarNumbers() {
        // given : 선행조건 기술
        int currentPageNumber = 1;
        int totalPages = 10;

        // when : 기능 수행
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(currentPageNumber, totalPages);

        // then : 결과 확인
        assertThat(paginationBarNumbers).containsExactly(0, 1, 2, 3, 4);
        assertThat(paginationService.currentBarLength()).isEqualTo(5);
    }

}
