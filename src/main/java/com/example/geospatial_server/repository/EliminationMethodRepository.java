package com.example.geospatial_server.repository;

import com.example.geospatial_server.model.entity.EliminationMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EliminationMethodRepository extends JpaRepository<EliminationMethodEntity, Integer> {
    Optional<EliminationMethodEntity> findByProblemAreaTypeIdAndName(Integer problemAreaTypeId, String name);

    Optional<EliminationMethodEntity> findByName(String name);
}