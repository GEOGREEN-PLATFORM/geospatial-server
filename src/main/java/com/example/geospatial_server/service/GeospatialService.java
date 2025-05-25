package com.example.geospatial_server.service;

import com.example.geospatial_server.model.dto.Density;
import com.example.geospatial_server.model.dto.ListMarkerResponse;
import com.example.geospatial_server.model.dto.MarkerDTO;
import com.example.geospatial_server.model.dto.OperatorStatisticDTO;
import com.example.geospatial_server.model.dto.RelatedTaskDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface GeospatialService {
    MarkerDTO createGeoPoint(MarkerDTO marker);

    MarkerDTO getGeoPoint(UUID geoPontId);

    MarkerDTO updateGeoPoint(UUID geoPointId, MarkerDTO marker);

    void deleteGeoPoint(UUID geoPointId);

    List<MarkerDTO> getAllGeoPoints(String problemAreaType);

    ListMarkerResponse getAllGeoPoints(int page, int size,
                                       String workStage, String landType,
                                       Density density, String eliminationMethod, UUID operatorId,
                                       OffsetDateTime startDate, OffsetDateTime endDate);

    void addRelatedTask(UUID geoPointId, RelatedTaskDTO request);

    OperatorStatisticDTO getStatistic(UUID operatorId);
}