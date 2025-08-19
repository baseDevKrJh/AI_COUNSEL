package org.aitest.ai_counsel.service;

import org.aitest.ai_counsel.domain.Counsel;
import org.aitest.ai_counsel.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CounselPredictionServiceTest {

    private CounselPredictionService predictionService;
    private CounselAnalysisService analysisService;
    private List<Counsel> counselHistory;
    private User testCounselor;

    @BeforeEach
    void setUp() {
        analysisService = new CounselAnalysisService();
        predictionService = new CounselPredictionService(analysisService);
        counselHistory = new ArrayList<>();

        testCounselor = new User();
        testCounselor.setId(1L);
        testCounselor.setEmail("test@example.com");

        // 과거 상담 기록 생성
        for (int i = 0; i < 5; i++) {
            Counsel counsel = new Counsel();
            counsel.setCounselor(testCounselor);
            counsel.setCustomerId(1L);
            counsel.setContent("상담 내용 " + i);
            counsel.setCounselDate(LocalDateTime.now().minusDays(i));
            counsel.setProductInfo("상품 " + i);
            counselHistory.add(counsel);
        }
    }

    @Test
    void predictNextCounsel_ShouldReturnValidPrediction() {
        // when
        CounselPredictionService.PredictionResult result = predictionService.predictNextCounsel(counselHistory);

        // then
        assertNotNull(result);
        assertNotNull(result.getPredictedType());
        assertNotNull(result.getDetails());
        assertFalse(result.getTopKeywords().isEmpty());
    }
}
