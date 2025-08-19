package org.aitest.ai_counsel.service;

import lombok.RequiredArgsConstructor;
import org.aitest.ai_counsel.domain.Counsel;
import org.aitest.ai_counsel.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselSecurityService {

    private final CounselService counselService;
    private final UserService userService;

    public boolean canAccessCounsel(Long counselId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Counsel counsel = counselService.getCounselById(counselId);
        User counselor = userService.getUserByUsername(authentication.getName());

        // 관리자는 모든 상담 내역에 접근 가능
        if (counselor.getRoles().contains("ADMIN")) {
            return true;
        }

        // 상담사는 자신의 상담 내역만 접근 가능
        return counsel.getCounselor().getId().equals(counselor.getId());
    }
}
