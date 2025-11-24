package com.example.sarabom.domain.property.domain;

import com.example.sarabom.domain.member.domain.Member;
import com.example.sarabom.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "tbl_property")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Property extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;  // 거주자

    @Embedded
    private Address address;  // 주소

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuildingType buildingType;  // 건물 타입

    private String buildingName;  // 건물명 (예: 래미안아파트)

    private Integer floorNumber;  // 층수

    private Integer roomCount;  // 방 개수

    @Column(length = 1000)
    private String description;  // 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;  // 상태

    public static Property of(Member member, Address address, BuildingType buildingType,
                               String buildingName, Integer floorNumber, Integer roomCount, String description) {
        return Property.builder()
                .member(member)
                .address(address)
                .buildingType(buildingType)
                .buildingName(buildingName)
                .floorNumber(floorNumber)
                .roomCount(roomCount)
                .description(description)
                .status(PropertyStatus.ACTIVE)
                .build();
    }

    /**
     * 거주지 정보 수정
     */
    public void update(Address address, BuildingType buildingType, String buildingName,
                      Integer floorNumber, Integer roomCount, String description) {
        this.address = address;
        this.buildingType = buildingType;
        this.buildingName = buildingName;
        this.floorNumber = floorNumber;
        this.roomCount = roomCount;
        this.description = description;
    }

    /**
     * 거주지 상태 수정
     */
    public void updatePropertyStatus(PropertyStatus status) {
        this.status = status;
    }
}
