package com.example.geospatialserver.repository;

import com.example.geospatialserver.model.entity.GeoPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GeoPointRepository extends JpaRepository<GeoPointEntity, UUID> {
    List<GeoPointEntity> findByProblemAreaTypeId(Integer problemAreaTypeId);
}
