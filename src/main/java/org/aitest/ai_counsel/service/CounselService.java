package org.aitest.ai_counsel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aitest.ai_counsel.domain.Counsel;
import org.aitest.ai_counsel.repository.CounselRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CounselService {

    private final CounselRepository counselRepository;
    private final CounselAnalysisService counselAnalysisService;
    private final ObjectMapper objectMapper;

    public Counsel saveCounsel(Counsel counsel) {
        return counselRepository.save(counsel);
    }

    @Transactional(readOnly = true)
    public Counsel getCounselById(Long id) {
        return counselRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Counsel not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Counsel> getCounsels(Long counselorId, Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        // 모든 필터 조건이 null인 경우 전체 조회
        if (counselorId == null && customerId == null && startDate == null && endDate == null) {
            return counselRepository.findAll();
        }

        // 조건에 따른 동적 조회
        if (counselorId != null && customerId != null && startDate != null && endDate != null) {
            return counselRepository.findByCounselorIdAndCustomerIdAndCounselDateBetween(
                    counselorId, customerId, startDate, endDate);
        } else if (counselorId != null) {
            return counselRepository.findByCounselorId(counselorId);
        } else if (customerId != null) {
            return counselRepository.findByCustomerId(customerId);
        } else if (startDate != null && endDate != null) {
            return counselRepository.findByCounselDateBetween(startDate, endDate);
        }

        return counselRepository.findAll();
    }

    public void deleteCounsel(Long id) {
        if (!counselRepository.existsById(id)) {
            throw new EntityNotFoundException("Counsel not found with id: " + id);
        }
        counselRepository.deleteById(id);
    }

    public Counsel analyzeCounsel(Long id) {
        Counsel counsel = getCounselById(id);

        try {
            // AI 분석 서비스를 통해 상담 내용 분석
            CounselAnalysisService.AnalysisResult analysisResult = counselAnalysisService.analyzeCounsel(counsel);

            // 분석 결과를 JSON 형식으로 변환
            Map<String, Object> analysisMap = new HashMap<>();
            analysisMap.put("상담 유형", analysisResult.getCounselType());
            analysisMap.put("고객 감정", analysisResult.getSentiment());
            analysisMap.put("주요 키워드", analysisResult.getKeywords());

            String analysisJson = objectMapper.writeValueAsString(analysisMap);

            counsel.setAnalysis(analysisJson);
            counsel.setPrediction("분석 완료됨");

        } catch (Exception e) {
            // 분석 실패 시 기본값 설정
            counsel.setAnalysis("분석 처리 중 오류 발생");
            counsel.setPrediction("예측 불가");
        }

        return saveCounsel(counsel);
    }

    @Transactional(readOnly = true)
    public List<Counsel> getCounselorHistory(Long counselorId) {
        return counselRepository.findByCounselorIdOrderByCounselDateDesc(counselorId);
    }

    @Transactional(readOnly = true)
    public List<Counsel> getCounselsByPeriod(LocalDateTime start, LocalDateTime end) {
        return counselRepository.findByCounselDateBetweenOrderByCounselDateDesc(start, end);
    }

    @Transactional(readOnly = true)
    public List<Counsel> getCounselsByProduct(String productInfo) {
        return counselRepository.findByProductInfoContainingIgnoreCaseOrderByCounselDateDesc(productInfo);
    }

    public Counsel predictNextCounselByCustomer(Long customerId) {
        List<Counsel> pastCounsels = counselRepository.findByCustomerIdOrderByCounselDateDesc(customerId);

        if (pastCounsels.isEmpty()) {
            throw new EntityNotFoundException("No counsel history found for customer: " + customerId);
        }

        // 가장 최근 상담을 기반으로 예측
        Counsel latestCounsel = pastCounsels.get(0);

        // 예측 결과를 새로운 Counsel 객체로 반환 (실제로는 저장하지 않음)
        Counsel predictedCounsel = new Counsel();
        predictedCounsel.setCustomerId(customerId);
        predictedCounsel.setContent("예측된 다음 상담 내용: " + latestCounsel.getProductInfo() + " 관련 후속 상담");
        predictedCounsel.setPrediction("과거 상담 패턴 기반 예측");
        predictedCounsel.setCounselDate(LocalDateTime.now().plusDays(7)); // 1주일 후 예측
        predictedCounsel.setProductInfo(latestCounsel.getProductInfo());

        return predictedCounsel;
    }

    // 권한 체크를 위한 메서드
    @Transactional(readOnly = true)
    public boolean isCounselorOfCounsel(Long counselId, String counselorEmail) {
        return counselRepository.findById(counselId)
                .map(counsel -> counsel.getCounselor().getEmail().equals(counselorEmail))
                .orElse(false);
    }
}