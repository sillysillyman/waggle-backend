package com.waggle.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
@Schema(description = "기본 응답 엔티티")
public abstract class BaseResponse<T> {

    @Schema(description = "성공 여부")
    protected final boolean isSuccess;

    @Schema(description = "응답 코드")
    protected final int code;

    @Schema(description = "응답 메시지")
    protected final String message;

    @Schema(description = "응답 데이터")
    protected final T payload;

    @Schema(description = "응답 시간", example = "2021-08-01T00:00:00Z")
    protected final String timestamp;

    protected BaseResponse(boolean isSuccess, int code, String message, T payload) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.payload = payload;
        this.timestamp = DateTimeFormatter.ISO_INSTANT
            .format(Instant.now().atZone(ZoneId.of("Asia/Seoul")));
    }
}
