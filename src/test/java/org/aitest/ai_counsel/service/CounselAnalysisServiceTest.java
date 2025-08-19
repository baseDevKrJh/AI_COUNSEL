package org.aitest.ai_counsel.service;

import org.aitest.ai_counsel.domain.Counsel;
import org.aitest.ai_counsel.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CounselAnalysisServiceTest {

    private CounselAnalysisService analysisService;
    private Counsel testCounsel;
    private User testCounselor;

    @BeforeEach
    void setUp() {
        analysisService = new CounselAnalysisService();

        testCounselor = new User();
        testCounselor.setId(1L);
        testCounselor.setEmail("test@example.com");

        testCounsel = new Counsel();
        testCounsel.setCounselor(testCounselor);
        testCounsel.setCustomerId(1L);
        testCounsel.setContent("고객이 펀드 상품에 대해 문의했습니다. 수익률과 위험도에 대해 설명했고, 고객은 만족스러워했습니다.");
    }

    @Test
    void analyzeCounsel_ShouldReturnValidAnalysis() {
        // when
        CounselAnalysisService.AnalysisResult result = analysisService.analyzeCounsel(testCounsel);

        // then
        assertNotNull(result);
        assertNotNull(result.getCounselType());
        assertNotNull(result.getSentiment());
        assertTrue(result.getKeywords().size() > 0);
    }
}
