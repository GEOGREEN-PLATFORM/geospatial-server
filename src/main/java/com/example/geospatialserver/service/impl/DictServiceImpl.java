package com.example.geospatialserver.service.impl;

import com.example.geospatialserver.model.entity.EliminationMethodEntity;
import com.example.geospatialserver.model.entity.LandTypeEntity;
import com.example.geospatialserver.model.entity.ProblemAreaTypeEntity;
import com.example.geospatialserver.model.entity.WorkStageEntity;
import com.example.geospatialserver.repository.LandTypeRepository;
import com.example.geospatialserver.repository.ProblemAreaTypeRepository;
import com.example.geospatialserver.repository.WorkStageRepository;
import com.example.geospatialserver.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {
    private final ProblemAreaTypeRepository problemAreaTypeRepository;
    private final LandTypeRepository landTypeRepository;
    private final WorkStageRepository workStageRepository;

    @Override
    public List<String> getProblemAreaTypes() {
        return problemAreaTypeRepository.findAll().stream().map(ProblemAreaTypeEntity::getName).toList();
    }

    @Override
    public List<String> getEliminationMethods(String problemAreaTypeName) {
        ProblemAreaTypeEntity problemAreaType = problemAreaTypeRepository.findByName(problemAreaTypeName)
                .orElseThrow(RuntimeException::new);
        return problemAreaType.getEliminationMethodEntities().stream().map(EliminationMethodEntity::getName).toList();
    }

    @Override
    public List<String> getLandTypes() {
        return landTypeRepository.findAll().stream().map(LandTypeEntity::getName).toList();
    }

    @Override
    public List<String> getWorkStages() {
        return workStageRepository.findAll().stream().map(WorkStageEntity::getName).toList();
    }
}
