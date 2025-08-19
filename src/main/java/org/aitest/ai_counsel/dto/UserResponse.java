package org.aitest.ai_counsel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.aitest.ai_counsel.domain.User;

@Getter
@Builder
@Schema(description = "사용자 응답")
public class UserResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "사용자명", example = "counselor001")
    private String username;

    @Schema(description = "역할", example = "ROLE_COUNSELOR")
    private String roles;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
