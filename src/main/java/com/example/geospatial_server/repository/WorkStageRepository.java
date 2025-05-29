package com.example.geospatial_server.repository;

import com.example.geospatial_server.model.entity.WorkStageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkStageRepository extends JpaRepository<WorkStageEntity, Integer> {
    Optional<WorkStageEntity> findByName(String name);
}
