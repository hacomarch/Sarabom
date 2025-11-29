package com.example.sarabom.domain.chat.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    @NotNull(message = "Room ID는 필수입니다.")
    private Long roomId;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
