package com.example.sarabom.domain.property.infrastructure;

import com.example.sarabom.domain.property.domain.Property;
import com.example.sarabom.domain.property.domain.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    /**
     * Member ID로 거주지 목록 조회 (Member fetch join으로 N+1 방지)
     */
    @Query("SELECT p FROM Property p JOIN FETCH p.member WHERE p.member.id = :memberId")
    List<Property> findByMember_Id(@Param("memberId") Long memberId);

    /**
     * Property ID로 조회 (Member fetch join)
     */
    @Query("SELECT p FROM Property p JOIN FETCH p.member WHERE p.id = :propertyId")
    Optional<Property> findByIdWithMember(@Param("propertyId") Long propertyId);

    /**
     * Property ID와 상태로 조회 (Member fetch join)
     */
    @Query("SELECT p FROM Property p JOIN FETCH p.member WHERE p.id = :propertyId AND p.status = :status")
    Optional<Property> findById(@Param("propertyId") Long propertyId, @Param("status") PropertyStatus status);

    /**
     * 특정 위치 기준 반경 내 거주지 검색 (MySQL ST_Distance_Sphere 사용)
     * @param latitude 기준 위도
     * @param longitude 기준 경도
     * @param radiusMeters 반경 (미터)
     * @return 반경 내 거주지 목록 (거리순 정렬)
     */
    @Query(value = """
        SELECT p.*,
               ST_Distance_Sphere(
                   POINT(:longitude, :latitude),
                   POINT(p.longitude, p.latitude)
               ) AS distance
        FROM tbl_property p
        WHERE p.status = 'ACTIVE'
          AND ST_Distance_Sphere(
                  POINT(:longitude, :latitude),
                  POINT(p.longitude, p.latitude)
              ) <= :radiusMeters
        ORDER BY distance
        """, nativeQuery = true)
    List<Property> findNearbyProperties(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radiusMeters") Double radiusMeters
    );
}
