package com.example.sarabom.domain.property.application.dto.response;

import com.example.sarabom.domain.property.domain.BuildingType;
import com.example.sarabom.domain.property.domain.PropertyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PropertyResponse {

    private Long propertyId;
    private String fullAddress;
    private String sido;
    private String sigungu;
    private String dong;
    private String detail;
    private Double latitude;
    private Double longitude;
    private BuildingType buildingType;
    private String buildingName;
    private Integer floorNumber;
    private Integer roomCount;
    private String description;
    private PropertyStatus status;
    private LocalDateTime createdAt;

    public static PropertyResponse of(Long propertyId, String fullAddress,
                                      String sido, String sigungu, String dong, String detail,
                                      Double latitude, Double longitude,
                                      BuildingType buildingType, String buildingName,
                                      Integer floorNumber, Integer roomCount, String description,
                                      PropertyStatus status, LocalDateTime createdAt) {
        return new PropertyResponse(propertyId, fullAddress, sido, sigungu, dong, detail,
                latitude, longitude, buildingType, buildingName, floorNumber, roomCount, description, status, createdAt);
    }
}
