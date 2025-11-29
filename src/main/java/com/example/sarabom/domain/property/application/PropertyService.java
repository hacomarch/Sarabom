package com.example.sarabom.domain.property.application;

import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.domain.member.infrastructure.MemberRepository;
import com.example.sarabom.domain.property.application.dto.request.RegisterPropertyRequest;
import com.example.sarabom.domain.property.application.dto.request.UpdatePropertyRequest;
import com.example.sarabom.domain.property.application.dto.response.PropertyResponse;
import com.example.sarabom.domain.property.domain.Address;
import com.example.sarabom.domain.property.domain.Property;
import com.example.sarabom.domain.property.domain.PropertyStatus;
import com.example.sarabom.domain.property.infrastructure.PropertyRepository;
import com.example.sarabom.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.sarabom.global.common.SuccessCode.SUCCESS_UPDATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final MemberRepository memberRepository;

    /**
     * 거주지 등록
     */
    @Transactional
    public ApiResponse<PropertyResponse> registerProperty(Long memberId, RegisterPropertyRequest request) {
        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        // 주소 VO 생성
        Address address = Address.of(
                request.getSido(),
                request.getSigungu(),
                request.getDong(),
                request.getDetail(),
                request.getLatitude(),
                request.getLongitude()
        );

        // Property 생성 및 저장
        Property property = Property.of(
                member,
                address,
                request.getBuildingType(),
                request.getBuildingName(),
                request.getFloorNumber(),
                request.getRoomCount(),
                request.getDescription()
        );
        propertyRepository.save(property);

        // 응답 생성
        PropertyResponse data = toResponse(property);
        return ApiResponse.success(data);
    }

    /**
     * 내 거주지 목록 조회
     */
    public ApiResponse<List<PropertyResponse>> getMyProperties(Long memberId) {
        List<Property> properties = propertyRepository.findByMember_Id(memberId);

        List<PropertyResponse> data = properties.stream()
                .map(this::toResponse)
                .toList();

        return ApiResponse.success(data);
    }

    /**
     * 거주지 상세 조회
     */
    public ApiResponse<PropertyResponse> getProperty(Long propertyId) {
        Property property = findById(propertyId);

        PropertyResponse data = toResponse(property);

        return ApiResponse.success(data);
    }

    /**
     * 거주지 수정
     */
    @Transactional
    public ApiResponse<Void> updateProperty(Long memberId, Long propertyId, UpdatePropertyRequest request) {
        Property property = findById(propertyId);

        // 본인 확인
        if (!property.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 거주지만 수정할 수 있습니다.");
        }

        // 주소 VO 생성
        Address address = Address.of(
                request.getSido(),
                request.getSigungu(),
                request.getDong(),
                request.getDetail(),
                request.getLatitude(),
                request.getLongitude()
        );

        // 업데이트
        property.update(
                address,
                request.getBuildingType(),
                request.getBuildingName(),
                request.getFloorNumber(),
                request.getRoomCount(),
                request.getDescription()
        );

        return ApiResponse.success(SUCCESS_UPDATE);
    }

    /**
     * 거주지 상태 수정
     */
    @Transactional
    public ApiResponse<Void> updatePropertyStatus(Long memberId, Long propertyId, PropertyStatus status) {
        Property property = findById(propertyId);

        // 본인 확인
        if (!property.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 거주지만 수정할 수 있습니다.");
        }

        // 거주지 상태 수정
        property.updatePropertyStatus(status);

        return ApiResponse.success(SUCCESS_UPDATE);
    }

    /**
     * 주변 거주지 검색 (반경 기준)
     */
    public ApiResponse<List<PropertyResponse>> searchNearbyProperties(Double latitude, Double longitude, Double radiusKm) {
        // km를 m로 변환
        Double radiusMeters = radiusKm * 1000;

        List<Property> properties = propertyRepository.findNearbyProperties(latitude, longitude, radiusMeters);

        List<PropertyResponse> data = properties.stream()
                .map(this::toResponse)
                .toList();

        return ApiResponse.success(data);
    }

    /**
     * Property → PropertyResponse 변환
     */
    private PropertyResponse toResponse(Property property) {
        return PropertyResponse.from(property);
    }

    private Property findById(Long propertyId) {
        return propertyRepository.findByIdWithMember(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("거주지를 찾을 수 없습니다."));
    }
}
