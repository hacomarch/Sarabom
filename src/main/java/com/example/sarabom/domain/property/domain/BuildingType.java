package com.example.sarabom.domain.property.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BuildingType {
    APARTMENT("아파트"),
    VILLA("빌라"),
    OFFICETEL("오피스텔"),
    ONE_ROOM("원룸"),
    TWO_ROOM("투룸");

    private final String description;
}
