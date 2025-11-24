package com.example.sarabom.domain.property.application.dto.request;

import com.example.sarabom.domain.property.domain.PropertyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePropertyStatusRequest {

    @NotNull(message = "상태를 입력해주세요.")
    private PropertyStatus status;
}
