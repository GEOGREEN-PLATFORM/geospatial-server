package com.example.geospatialserver.service;

import com.example.geospatialserver.model.dto.MarkerDTO;

import java.util.List;
import java.util.UUID;

public interface GeospatialService {
    MarkerDTO createGeoPoint(MarkerDTO marker);

    MarkerDTO getGeoPoint(UUID geoPontId);

    MarkerDTO updateGeoPoint(MarkerDTO marker);

    void deleteGeoPoint(UUID geoPointId);

    List<MarkerDTO> getAllGeoPoints(String problemAreaType);
}
