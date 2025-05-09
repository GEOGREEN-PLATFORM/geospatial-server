package com.example.geospatialserver.service;

import com.example.geospatialserver.model.dto.Density;
import com.example.geospatialserver.model.dto.ListMarkerResponse;
import com.example.geospatialserver.model.dto.MarkerDTO;
import com.example.geospatialserver.model.dto.RelatedTaskDTO;

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
                                       Density density, String eliminationMethod,
                                       OffsetDateTime startDate, OffsetDateTime endDate);

    void addRelatedTask(UUID geoPointId, RelatedTaskDTO request);
}