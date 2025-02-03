package com.example.geospatialserver.repository;

import com.example.geospatialserver.model.entity.LandTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandTypeRepository extends JpaRepository<LandTypeEntity, Integer> {
    Optional<LandTypeEntity> findByName(String name);
}
