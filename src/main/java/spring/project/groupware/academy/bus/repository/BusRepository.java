package spring.project.groupware.academy.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.bus.entity.BusEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {

    Optional<BusEntity> findBybusRouteNm(String busNo);

}
