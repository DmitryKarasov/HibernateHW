package com.karasov.hibernatehw.repository;

import com.karasov.hibernatehw.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findCityByName(String name);

}
