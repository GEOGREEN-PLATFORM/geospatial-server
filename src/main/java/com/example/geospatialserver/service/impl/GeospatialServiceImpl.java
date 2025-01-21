package com.example.geospatialserver.service.impl;

import com.example.geospatialserver.mappers.GeoPointMapper;
import com.example.geospatialserver.model.dto.MarkerDTO;
import com.example.geospatialserver.repository.EliminationMethodRepository;
import com.example.geospatialserver.repository.GeoPointRepository;
import com.example.geospatialserver.repository.LandTypeRepository;
import com.example.geospatialserver.repository.ProblemAreaTypeRepository;
import com.example.geospatialserver.repository.WorkStageRepository;
import com.example.geospatialserver.service.GeospatialService;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeospatialServiceImpl implements GeospatialService {
    private final GeoPointRepository geoPointRepository;
    private final LandTypeRepository landTypeRepository;
    private final WorkStageRepository workStageRepository;
    private final EliminationMethodRepository eliminationMethodRepository;
    private final ProblemAreaTypeRepository problemAreaTypeRepository;
    private final GeoPointMapper geoPointMapper;

    @Transactional
    @Override
    public MarkerDTO createGeoPoint(MarkerDTO marker) {
        var landType = landTypeRepository.findByName(marker.getDetails().getLandType())
                .orElseThrow(() -> new NoResultException("Неразрешённый тип земли"));
        var workStage = workStageRepository.findByName(marker.getDetails().getWorkStage())
                .orElseThrow(() -> new NoResultException("Неразрешённый статус задачи"));
        var problemAreaType = problemAreaTypeRepository.findByName(marker.getDetails().getProblemAreaType())
                .orElseThrow(() -> new NoResultException("Неразрешённый тип проблемы"));
        var eliminationMethod = eliminationMethodRepository
                .findByProblemAreaTypeIdAndName(problemAreaType.getId(), marker.getDetails().getEliminationMethod())
                .orElseThrow(() -> new NoResultException("Неразрешённый способ обработки"));
        var geoPointEntity = geoPointMapper.toEntity(marker);
        geoPointEntity.setLandType(landType);
        geoPointEntity.setWorkStage(workStage);
        geoPointEntity.setEliminationMethod(eliminationMethod);
        geoPointEntity.setProblemAreaType(problemAreaType);
        //todo square посчитать
        return geoPointMapper.toDTO(geoPointRepository.save(geoPointEntity));
    }

    @Override
    public MarkerDTO getGeoPoint(UUID geoPointId) {
        var geoPointEntity = geoPointRepository.findById(geoPointId)
                .orElseThrow(() -> new NoResultException(String.format("Точка с id {%s} не найдена", geoPointId)));
        return geoPointMapper.toDTO(geoPointEntity);
    }

    @Transactional
    @Override
    public MarkerDTO updateGeoPoint(UUID geoPointId, MarkerDTO marker) {
        var geoPointEntity = geoPointRepository.findById(geoPointId)
                .orElseThrow(() -> new NoResultException(String.format("Точка с id {%s} не найдена", marker.getId())));

        geoPointEntity = geoPointMapper.mergeGeoPoint(geoPointEntity, marker);
        var details = marker.getDetails();
        if (details != null) {
            if (details.getLandType() != null) {
                var landType = landTypeRepository.findByName(details.getLandType())
                        .orElseThrow(() -> new NoResultException("Неразрешённый тип земли"));
                geoPointEntity.setLandType(landType);
            }
            if (details.getWorkStage() != null) {
                var workStage = workStageRepository.findByName(details.getWorkStage())
                        .orElseThrow(() -> new NoResultException("Неразрешённый статус задачи"));
                geoPointEntity.setWorkStage(workStage);
            }
            if (details.getProblemAreaType() != null) {
                var problemAreaType = problemAreaTypeRepository.findByName(details.getProblemAreaType())
                        .orElseThrow(() -> new NoResultException("Неразрешённый тип проблемы"));
                geoPointEntity.setProblemAreaType(problemAreaType);
            }
            if (details.getEliminationMethod() != null) {
                var eliminationMethod = eliminationMethodRepository
                        .findByProblemAreaTypeIdAndName(geoPointEntity.getProblemAreaType().getId(), details.getEliminationMethod())
                        .orElseThrow(() -> new NoResultException("Неразрешённый способ обработки"));
                geoPointEntity.setEliminationMethod(eliminationMethod);
            }
        }
        return geoPointMapper.toDTO(geoPointRepository.save(geoPointEntity));
    }

    @Transactional
    @Override
    public void deleteGeoPoint(UUID geoPointId) {
        if (!geoPointRepository.existsById(geoPointId)) {
            throw new NoResultException(String.format("Точка с id {%s} не найдена", geoPointId));
        }
        geoPointRepository.deleteById(geoPointId);
    }

    @Override
    public List<MarkerDTO> getAllGeoPoints(String problemAreaType) {
        if (problemAreaType == null) {
            return geoPointRepository.findAll().stream().map(geoPointMapper::toDTO).toList();
        }
        var problemAreaTypeEntity = problemAreaTypeRepository.findByName(problemAreaType)
                .orElseThrow(() -> new NoResultException("Неразрешённый тип проблемы"));
        return geoPointRepository.findByProblemAreaTypeId(problemAreaTypeEntity.getId())
                .stream().map(geoPointMapper::toDTO).toList();
    }
}
