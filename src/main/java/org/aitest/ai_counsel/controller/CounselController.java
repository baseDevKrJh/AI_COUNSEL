package org.aitest.ai_counsel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aitest.ai_counsel.domain.Counsel;
import org.aitest.ai_counsel.domain.User;
import org.aitest.ai_counsel.dto.CounselRequest;
import org.aitest.ai_counsel.dto.CounselResponse;
import org.aitest.ai_counsel.service.CounselService;
import org.aitest.ai_counsel.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "상담 관리", description = "상담 내용 관리 및 분석 API")
@RestController
@RequestMapping("/api/counsels")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CounselController {

    private final CounselService counselService;
    private final UserService userService;

    @Operation(summary = "상담 내용 저장", description = "새로운 상담 내용을 저장합니다.")
    @PostMapping
    public ResponseEntity<CounselResponse> createCounsel(@Valid @RequestBody CounselRequest request,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        User counselor = userService.getUserByUsername(userDetails.getUsername());

        Counsel counsel = new Counsel();
        counsel.setCounselor(counselor);
        counsel.setCustomerId(request.getCustomerId());
        counsel.setContent(request.getContent());
        counsel.setCounselDate(request.getCounselDate() != null ? request.getCounselDate() : LocalDateTime.now());
        counsel.setProductInfo(request.getProductInfo());

        Counsel saved = counselService.saveCounsel(counsel);
        return ResponseEntity.ok(CounselResponse.from(saved));
    }

    @Operation(summary = "상담 내용 목록 조회", description = "상담 내용 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CounselResponse>> getCounsels(
            @Parameter(description = "상담사 ID") @RequestParam(required = false) Long counselorId,
            @Parameter(description = "고객 ID") @RequestParam(required = false) Long customerId,
            @Parameter(description = "시작 날짜 (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
            @Parameter(description = "종료 날짜 (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {

        List<Counsel> counsels = counselService.getCounsels(counselorId, customerId, startDate, endDate);
        List<CounselResponse> responses = counsels.stream()
                .map(CounselResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "상담 내용 상세 조회", description = "특정 상담 내용을 상세 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CounselResponse> getCounselById(@PathVariable Long id) {
        try {
            Counsel counsel = counselService.getCounselById(id);
            return ResponseEntity.ok(CounselResponse.from(counsel));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "상담 내용 수정", description = "기존 상담 내용을 수정합니다.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @counselService.isCounselorOfCounsel(#id, authentication.name)")
    public ResponseEntity<CounselResponse> updateCounsel(@PathVariable Long id,
                                                       @Valid @RequestBody CounselRequest request,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Counsel counsel = counselService.getCounselById(id);
            counsel.setCustomerId(request.getCustomerId());
            counsel.setContent(request.getContent());
            counsel.setCounselDate(request.getCounselDate() != null ? request.getCounselDate() : counsel.getCounselDate());
            counsel.setProductInfo(request.getProductInfo());

            Counsel updated = counselService.saveCounsel(counsel);
            return ResponseEntity.ok(CounselResponse.from(updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "상담 내용 삭제", description = "특정 상담 내용을 삭제합니다.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @counselService.isCounselorOfCounsel(#id, authentication.name)")
    public ResponseEntity<Void> deleteCounsel(@PathVariable Long id) {
        try {
            counselService.deleteCounsel(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "상담 내용 분석", description = "상담 내용을 AI로 분석하고 예측 결과를 반환합니다.")
    @GetMapping("/{id}/analysis")
    public ResponseEntity<CounselResponse> analyzeCounsel(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Counsel analyzed = counselService.analyzeCounsel(id);
            return ResponseEntity.ok(CounselResponse.from(analyzed));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "상담사별 상담 내역 조회", description = "특정 상담사의 상담 내역을 조회합니다.")
    @GetMapping("/counselor/{counselorId}")
    @PreAuthorize("hasRole('ADMIN')")  // 관리자만 다른 상담사의 상담 내역을 조회할 수 있도록 설정
    public ResponseEntity<List<CounselResponse>> getCounselorHistory(
            @Parameter(description = "상담사 ID") @PathVariable Long counselorId) {
        List<CounselResponse> responses = counselService.getCounselorHistory(counselorId)
                .stream()
                .map(CounselResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "기간별 상담 내역 조회", description = "지정된 기간의 상담 내역을 조회합니다.")
    @GetMapping("/period")
    @PreAuthorize("hasAnyRole('ADMIN', 'COUNSELOR')")  // 관리자와 상담사만 조회 가능
    public ResponseEntity<List<CounselResponse>> getCounselsByPeriod(
            @Parameter(description = "시작일") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Parameter(description = "종료일") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<CounselResponse> responses = counselService.getCounselsByPeriod(start, end)
                .stream()
                .map(CounselResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "상품별 상담 내역 조회", description = "특정 상품 관련 상담 내역을 조회합니다.")
    @GetMapping("/product")
    @PreAuthorize("hasAnyRole('ADMIN', 'COUNSELOR')")  // 관리자와 상담사만 조회 가능
    public ResponseEntity<List<CounselResponse>> getCounselsByProduct(
            @Parameter(description = "상품 정보") @RequestParam String productInfo) {
        List<CounselResponse> responses = counselService.getCounselsByProduct(productInfo)
                .stream()
                .map(CounselResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "다음 상담 예측", description = "과거 상담 내역을 기반으로 다음 상담을 예측합니다.")
    @GetMapping("/{customerId}/prediction")
    public ResponseEntity<CounselResponse> predictNextCounsel(
            @Parameter(description = "고객 ID") @PathVariable Long customerId) {
        Counsel prediction = counselService.predictNextCounselByCustomer(customerId);
        return ResponseEntity.ok(CounselResponse.from(prediction));
    }
}
