package com.example.geospatialserver.repository;

import com.example.geospatialserver.model.entity.EliminationMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EliminationMethodRepository extends JpaRepository<EliminationMethodEntity, Integer> {
    Optional<EliminationMethodEntity> findByProblemAreaTypeIdAndName(Integer problemAreaTypeId, String name);

    Optional<EliminationMethodEntity> findByName(String name);
}