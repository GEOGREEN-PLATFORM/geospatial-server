package com.example.geospatialserver.service.impl;

import com.example.geospatialserver.mappers.GeoPointMapper;
import com.example.geospatialserver.model.dto.MarkerDTO;
import com.example.geospatialserver.repository.EliminationMethodRepository;
import com.example.geospatialserver.repository.GeoPointRepository;
import com.example.geospatialserver.repository.LandTypeRepository;
import com.example.geospatialserver.repository.ProblemAreaTypeRepository;
import com.example.geospatialserver.repository.WorkStageRepository;
import com.example.geospatialserver.service.GeospatialService;
import jakarta.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("Неразрешённый тип земли"));
        var workStage = workStageRepository.findByName(marker.getDetails().getWorkStage())
                .orElseThrow(() -> new EntityNotFoundException("Неразрешённый статус задачи"));
        var problemAreaType = problemAreaTypeRepository.findByName(marker.getDetails().getProblemAreaType())
                .orElseThrow(() -> new EntityNotFoundException("Неразрешённый тип проблемы"));
        var eliminationMethod = eliminationMethodRepository
                .findByProblemAreaTypeIdAndName(problemAreaType.getId(), marker.getDetails().getEliminationMethod())
                .orElseThrow(() -> new EntityNotFoundException("Неразрешённый способ обработки"));
        var geoPointEntity = geoPointMapper.toEntity(marker);
        geoPointEntity.setLandType(landType);
        geoPointEntity.setWorkStage(workStage);
        geoPointEntity.setEliminationMethod(eliminationMethod);
        geoPointEntity.setProblemAreaType(problemAreaType);
        geoPointEntity.setSquare(calculateSquare(marker.getCoordinates()));
        return geoPointMapper.toDTO(geoPointRepository.save(geoPointEntity));
    }

    private double calculateSquare(List<List<Double>> coordinates) {
        int n = coordinates.size();
        if (n < 3) {
            throw new IllegalArgumentException("coordinates должены иметь как минимум 3 точки");
        }
        double area = 0;
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area += (coordinates.get(i).get(0) * coordinates.get(j).get(1)) - (coordinates.get(j).get(0) * coordinates.get(i).get(1));
        }
        return Math.round(Math.abs(area) / 2.0 * 100.0) / 100.0;
    }

    @Override
    public MarkerDTO getGeoPoint(UUID geoPointId) {
        var geoPointEntity = geoPointRepository.findById(geoPointId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Точка с id {%s} не найдена", geoPointId)));
        return geoPointMapper.toDTO(geoPointEntity);
    }

    @Transactional
    @Override
    public MarkerDTO updateGeoPoint(UUID geoPointId, MarkerDTO marker) {
        var geoPointEntity = geoPointRepository.findById(geoPointId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Точка с id {%s} не найдена", marker.getId())));

        geoPointEntity = geoPointMapper.mergeGeoPoint(geoPointEntity, marker);
        var details = marker.getDetails();
        if (details != null) {
            if (details.getLandType() != null) {
                var landType = landTypeRepository.findByName(details.getLandType())
                        .orElseThrow(() -> new EntityNotFoundException("Неразрешённый тип земли"));
                geoPointEntity.setLandType(landType);
            }
            if (details.getWorkStage() != null) {
                var workStage = workStageRepository.findByName(details.getWorkStage())
                        .orElseThrow(() -> new EntityNotFoundException("Неразрешённый статус задачи"));
                geoPointEntity.setWorkStage(workStage);
            }
            if (details.getProblemAreaType() != null) {
                var problemAreaType = problemAreaTypeRepository.findByName(details.getProblemAreaType())
                        .orElseThrow(() -> new EntityNotFoundException("Неразрешённый тип проблемы"));
                geoPointEntity.setProblemAreaType(problemAreaType);
            }
            if (details.getEliminationMethod() != null) {
                var eliminationMethod = eliminationMethodRepository
                        .findByProblemAreaTypeIdAndName(geoPointEntity.getProblemAreaType().getId(), details.getEliminationMethod())
                        .orElseThrow(() -> new EntityNotFoundException("Неразрешённый способ обработки"));
                geoPointEntity.setEliminationMethod(eliminationMethod);
            }
        }
        if (marker.getCoordinates() != null) {
            geoPointEntity.setSquare(calculateSquare(marker.getCoordinates()));
        }
        return geoPointMapper.toDTO(geoPointRepository.save(geoPointEntity));
    }

    @Transactional
    @Override
    public void deleteGeoPoint(UUID geoPointId) {
        if (!geoPointRepository.existsById(geoPointId)) {
            throw new EntityNotFoundException(String.format("Точка с id {%s} не найдена", geoPointId));
        }
        geoPointRepository.deleteById(geoPointId);
    }

    @Override
    public List<MarkerDTO> getAllGeoPoints(String problemAreaType) {
        if (problemAreaType == null) {
            return geoPointRepository.findAll().stream().map(geoPointMapper::toDTO).toList();
        }
        var problemAreaTypeEntity = problemAreaTypeRepository.findByName(problemAreaType)
                .orElseThrow(() -> new EntityNotFoundException("Неразрешённый тип проблемы"));
        return geoPointRepository.findByProblemAreaTypeId(problemAreaTypeEntity.getId())
                .stream().map(geoPointMapper::toDTO).toList();
    }
}
