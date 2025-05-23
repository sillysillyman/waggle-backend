package com.waggle.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waggle.domain.reference.enums.Industry;
import com.waggle.domain.reference.enums.Sido;
import com.waggle.domain.reference.enums.Skill;
import com.waggle.domain.reference.enums.WorkTime;
import com.waggle.domain.reference.enums.WorkWay;
import com.waggle.domain.user.UserInfo;
import com.waggle.domain.user.entity.UserDayOfWeek;
import com.waggle.domain.user.entity.UserIndustry;
import com.waggle.domain.user.entity.UserSkill;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Schema(description = "사용자 정보 응답 DTO")
public record UserResponseDto(
    @Schema(description = "사용자 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    @JsonProperty("id")
    UUID id,

    @Schema(description = "OAuth 제공자", example = "google")
    @JsonProperty("provider")
    String provider,

    @Schema(description = "OAuth ID", example = "1234567890")
    @JsonProperty("provider_id")
    String providerId,

    @Schema(description = "프로필 이미지 URL")
    @JsonProperty("profile_img_url")
    String profileImageUrl,

    @Schema(description = "사용자 이름", example = "홍길동")
    @JsonProperty("name")
    String name,

    @Schema(description = "사용자 이메일", example = "hong@gmail.com")
    @JsonProperty("email")
    String email,

    @Schema(description = "사용자 직무 정보")
    @JsonProperty("job_roles")
    Set<UserJobRoleDto> userJobRoleDtos,

    @Schema(description = "사용자 관심 산업 정보")
    @JsonProperty("industries")
    Set<Industry> industries,

    @Schema(description = "사용자 보유 기술 정보")
    @JsonProperty("skills")
    Set<Skill> skills,

    @Schema(description = "사용자 선호 요일 정보")
    @JsonProperty("days_of_week")
    Set<DayOfWeek> daysOfWeek,

    @Schema(description = "사용자 선호 작업 시간 정보")
    @JsonProperty("preferred_work_time")
    WorkTime workTime,

    @Schema(description = "사용자 선호 작업 방식 정보")
    @JsonProperty("preferred_work_way")
    WorkWay workWay,

    @Schema(description = "사용자 지역 정보")
    @JsonProperty("preferred_sido")
    Sido sido,

    @Schema(description = "사용자 소개 키워드 정보")
    @JsonProperty("introductions")
    UserIntroductionDto userIntroductionDto,

    @Schema(description = "사용자 자기소개", example = "안녕하세요.")
    @JsonProperty("detail")
    String detail,

    @Schema(description = "사용자 포트폴리오 정보")
    @JsonProperty("portfolios")
    Set<UserPortfolioDto> userPortfolioDtos,

    @Schema(description = "생성일자", example = "2021-07-01T00:00:00")
    @JsonProperty("created_at")
    LocalDateTime createdAt,

    @Schema(description = "수정일자", example = "2021-07-01T00:00:00")
    @JsonProperty("updated_at")
    LocalDateTime updatedAt
) {

    public static UserResponseDto from(UserInfo userInfo) {
        return new UserResponseDto(
            userInfo.user().getId(),
            userInfo.user().getProvider(),
            userInfo.user().getProviderId(),
            userInfo.user().getProfileImageUrl(),
            userInfo.user().getName(),
            userInfo.user().getEmail(),
            userInfo.userJobRoles().stream()
                .map(UserJobRoleDto::from)
                .sorted(Comparator.comparing(UserJobRoleDto::jobRole))
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            userInfo.userIndustries().stream()
                .map(UserIndustry::getIndustry)
                .sorted(Comparator.comparing(Enum::name))
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            userInfo.userSkills().stream()
                .map(UserSkill::getSkill)
                .sorted(Comparator.comparing(Enum::name))
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            userInfo.userDaysOfWeek().stream()
                .map(UserDayOfWeek::getDayOfWeek)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            userInfo.user().getWorkTime(),
            userInfo.user().getWorkWay(),
            userInfo.user().getSido(),
            UserIntroductionDto.from(userInfo.userIntroductions()),
            userInfo.user().getDetail(),
            userInfo.userPortfolios().stream()
                .map(UserPortfolioDto::from)
                .sorted(Comparator.comparing(UserPortfolioDto::portfolioType))
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            userInfo.user().getCreatedAt(),
            userInfo.user().getUpdatedAt()
        );
    }
}
