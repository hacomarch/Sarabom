package com.example.sarabom.domain.property.application.dto.response;

import com.example.sarabom.domain.property.domain.BuildingType;
import com.example.sarabom.domain.property.domain.Property;
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

    public static PropertyResponse from(Property property) {
        return new PropertyResponse(
                property.getId(),
                property.getAddress().getFullAddress(),
                property.getAddress().getSido(),
                property.getAddress().getSigungu(),
                property.getAddress().getDong(),
                property.getAddress().getDetail(),
                property.getAddress().getLatitude(),
                property.getAddress().getLongitude(),
                property.getBuildingType(),
                property.getBuildingName(),
                property.getFloorNumber(),
                property.getRoomCount(),
                property.getDescription(),
                property.getStatus(),
                property.getCreatedAt()
        );
    }
}
