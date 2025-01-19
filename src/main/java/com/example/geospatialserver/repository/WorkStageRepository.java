package com.example.geospatialserver.repository;

import com.example.geospatialserver.model.entity.WorkStageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkStageRepository extends JpaRepository<WorkStageEntity, Integer> {
}
