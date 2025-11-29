package com.example.sarabom.domain.chat.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRoomRequest {

    @NotNull(message = "Property ID는 필수입니다.")
    private Long propertyId;
}
