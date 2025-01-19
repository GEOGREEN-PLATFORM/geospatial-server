package com.example.geospatialserver.repository;

import com.example.geospatialserver.model.entity.ProblemAreaTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemAreaTypeRepository extends JpaRepository<ProblemAreaTypeEntity, Integer> {
}
