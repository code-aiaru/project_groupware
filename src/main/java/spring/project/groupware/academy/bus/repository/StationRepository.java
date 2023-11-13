package spring.project.groupware.academy.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.bus.entity.StationEntity;

import java.util.Optional;

public interface StationRepository extends JpaRepository<StationEntity, Long> {

    Optional<StationEntity> findBybusRouteNm(String busRouteNm);

    Optional<StationEntity> findBybusRouteNmAndStationNm(String busRouteNm, String stationNm);

    int countBybusRouteNm(String busRouteNm);

    Optional<StationEntity> findBybusRouteNmAndSeq(String busRouteNm, String seq);

    StationEntity findBybusRouteNmAndStation(String busRouteId, String stId);
}
