package spring.project.groupware.academy.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

    WeatherEntity findByCityName(String cityName);
}
