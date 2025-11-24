package com.example.sarabom.domain.property.presentation;

import com.example.sarabom.domain.property.application.PropertyService;
import com.example.sarabom.domain.property.application.dto.request.RegisterPropertyRequest;
import com.example.sarabom.domain.property.application.dto.request.UpdatePropertyRequest;
import com.example.sarabom.domain.property.application.dto.request.UpdatePropertyStatusRequest;
import com.example.sarabom.domain.property.application.dto.response.PropertyResponse;
import com.example.sarabom.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "거주지 관리", description = "거주지 CRUD API")
@RequestMapping("/api/v1/property")
@RestController
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @Operation(summary = "거주지 등록", description = "내가 살고 있는 거주지를 등록합니다.")
    @PostMapping
    public ApiResponse<PropertyResponse> registerProperty(
            Authentication authentication,
            @Valid @RequestBody RegisterPropertyRequest request) {
        Long memberId = (Long) authentication.getPrincipal();
        return propertyService.registerProperty(memberId, request);
    }

    @Operation(summary = "내 거주지 목록 조회", description = "내가 등록한 거주지 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<PropertyResponse>> getMyProperties(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        return propertyService.getMyProperties(memberId);
    }

    @Operation(summary = "거주지 상세 조회", description = "특정 거주지의 상세 정보를 조회합니다.")
    @GetMapping("/{propertyId}")
    public ApiResponse<PropertyResponse> getProperty(
            @Parameter(description = "거주지 ID", required = true) @PathVariable Long propertyId) {
        return propertyService.getProperty(propertyId);
    }

    @Operation(summary = "거주지 수정", description = "내 거주지 정보를 수정합니다.")
    @PutMapping("/{propertyId}")
    public ApiResponse<Void> updateProperty(
            Authentication authentication,
            @Parameter(description = "거주지 ID", required = true) @PathVariable Long propertyId,
            @Valid @RequestBody UpdatePropertyRequest request) {
        Long memberId = (Long) authentication.getPrincipal();
        return propertyService.updateProperty(memberId, propertyId, request);
    }

    @Operation(summary = "거주지 상태 수정", description = "내 거주지 상태를 수정합니다.")
    @PatchMapping("/{propertyId}/status")
    public ApiResponse<Void> updatePropertyStatus(
            Authentication authentication,
            @Parameter(description = "거주지 ID", required = true) @PathVariable Long propertyId,
            @Valid @RequestBody UpdatePropertyStatusRequest request) {
        Long memberId = (Long) authentication.getPrincipal();
        return propertyService.updatePropertyStatus(memberId, propertyId, request.getStatus());
    }

    @Operation(summary = "주변 거주지 검색", description = "특정 위치 기준 반경 내 거주지를 검색합니다.")
    @GetMapping("/search/nearby")
    public ApiResponse<List<PropertyResponse>> searchNearbyProperties(
            @Parameter(description = "위도", required = true) @RequestParam Double latitude,
            @Parameter(description = "경도", required = true) @RequestParam Double longitude,
            @Parameter(description = "반경 (km)", required = true) @RequestParam Double radiusKm) {
        return propertyService.searchNearbyProperties(latitude, longitude, radiusKm);
    }
}
