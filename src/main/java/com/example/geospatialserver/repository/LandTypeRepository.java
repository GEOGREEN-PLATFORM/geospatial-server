package com.example.geospatialserver.repository;

import com.example.geospatialserver.model.entity.LandTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandTypeRepository extends JpaRepository<LandTypeEntity, Integer> {
}
