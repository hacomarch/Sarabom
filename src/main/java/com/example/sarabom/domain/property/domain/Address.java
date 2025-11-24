package com.example.sarabom.domain.property.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(nullable = false)
    private String sido;  // 시/도 (예: 서울특별시)

    @Column(nullable = false)
    private String sigungu;  // 시/군/구 (예: 강남구)

    @Column(nullable = false)
    private String dong;  // 동 (예: 역삼동)

    @Column(nullable = false)
    private String detail;  // 상세 주소 (예: 123-45)

    @Column(nullable = false)
    private Double latitude;  // 위도 (-90 ~ 90)

    @Column(nullable = false)
    private Double longitude;  // 경도 (-180 ~ 180)

    private Address(String sido, String sigungu, String dong, String detail, Double latitude, Double longitude) {
        validate(sido, sigungu, dong, detail, latitude, longitude);
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.detail = detail;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Address of(String sido, String sigungu, String dong, String detail, Double latitude, Double longitude) {
        return new Address(sido, sigungu, dong, detail, latitude, longitude);
    }

    private void validate(String sido, String sigungu, String dong, String detail, Double latitude, Double longitude) {
        if (sido == null || sido.isBlank()) {
            throw new IllegalArgumentException("시/도는 필수입니다.");
        }
        if (sigungu == null || sigungu.isBlank()) {
            throw new IllegalArgumentException("시/군/구는 필수입니다.");
        }
        if (dong == null || dong.isBlank()) {
            throw new IllegalArgumentException("동은 필수입니다.");
        }
        if (detail == null || detail.isBlank()) {
            throw new IllegalArgumentException("상세 주소는 필수입니다.");
        }
        if (latitude == null || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("위도는 -90 ~ 90 사이의 값이어야 합니다.");
        }
        if (longitude == null || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("경도는 -180 ~ 180 사이의 값이어야 합니다.");
        }
    }

    /**
     * 전체 주소 문자열 반환
     */
    public String getFullAddress() {
        return String.format("%s %s %s %s", sido, sigungu, dong, detail);
    }
}
