package com.example.sarabom.domain.property.application.dto.request;

import com.example.sarabom.domain.property.domain.BuildingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPropertyRequest {

    @NotBlank(message = "시/도를 입력해주세요.")
    private String sido;

    @NotBlank(message = "시/군/구를 입력해주세요.")
    private String sigungu;

    @NotBlank(message = "동을 입력해주세요.")
    private String dong;

    @NotBlank(message = "상세 주소를 입력해주세요.")
    private String detail;

    @NotNull(message = "위도를 입력해주세요.")
    private Double latitude;

    @NotNull(message = "경도를 입력해주세요.")
    private Double longitude;

    @NotNull(message = "건물 타입을 선택해주세요.")
    private BuildingType buildingType;

    private String buildingName;

    private Integer floorNumber;

    private Integer roomCount;

    private String description;
}
