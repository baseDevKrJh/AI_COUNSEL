package org.aitest.ai_counsel.repository;

import org.aitest.ai_counsel.domain.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CounselRepository extends JpaRepository<Counsel, Long> {
    // 상담사 ID로 상담 내역 조회
    List<Counsel> findByCounselorId(Long counselorId);

    // 고객 ID로 상담 내역 조회
    List<Counsel> findByCustomerId(Long customerId);

    // 상담 날짜 범위로 조회
    List<Counsel> findByCounselDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 상담사 ID와 고객 ID, 날짜 범위로 복합 조회
    List<Counsel> findByCounselorIdAndCustomerIdAndCounselDateBetween(
        Long counselorId, Long customerId, LocalDateTime startDate, LocalDateTime endDate);

    // 상담사별 상담 내역 (최신순)
    List<Counsel> findByCounselorIdOrderByCounselDateDesc(Long counselorId);

    // 고객별 상담 내역 (최신순)
    List<Counsel> findByCustomerIdOrderByCounselDateDesc(Long customerId);

    // 기간별 상담 내역 (최신순)
    List<Counsel> findByCounselDateBetweenOrderByCounselDateDesc(LocalDateTime start, LocalDateTime end);

    // 상품명으로 검색 (대소문자 무시, 부분 일치)
    List<Counsel> findByProductInfoContainingIgnoreCaseOrderByCounselDateDesc(String productInfo);

    // 상품명으로 검색 (부분 일치)
    List<Counsel> findByProductInfoContaining(String productInfo);

    // 상담사 Entity를 통한 조회 (JPA 관계 활용)
    @Query("SELECT c FROM Counsel c WHERE c.counselor.id = :counselorId ORDER BY c.counselDate DESC")
    List<Counsel> findByCounselor_IdOrderByCounselDateDesc(@Param("counselorId") Long counselorId);

    // 분석이 완료된 상담 조회
    @Query("SELECT c FROM Counsel c WHERE c.analysis IS NOT NULL")
    List<Counsel> findAnalyzedCounsels();

    // 특정 기간의 특정 상담사 상담 건수
    @Query("SELECT COUNT(c) FROM Counsel c WHERE c.counselor.id = :counselorId AND c.counselDate BETWEEN :start AND :end")
    Long countByCounselorIdAndDateBetween(@Param("counselorId") Long counselorId,
                                         @Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    // 최근 N개의 상담 조회
    @Query("SELECT c FROM Counsel c ORDER BY c.counselDate DESC LIMIT :limit")
    List<Counsel> findRecentCounsels(@Param("limit") int limit);
}
