package com.example.geospatialserver.repository;

import com.example.geospatialserver.model.entity.GeoPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeoPointRepository extends JpaRepository<GeoPointEntity, UUID> {
}
