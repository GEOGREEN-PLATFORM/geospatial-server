package com.example.geospatial_server.service;

import com.example.geospatial_server.model.dto.MarkerDTO;
import com.example.geospatial_server.model.dto.enums.ProblemAreaType;

import java.util.List;
import java.util.UUID;

public interface GeospatialService {
    MarkerDTO getGeoPointInfo(UUID geoPontId);

    MarkerDTO saveGeoPointInfo(MarkerDTO markerDTO);

    List<MarkerDTO> getAllGeoPointsByProblemAreaType(ProblemAreaType problemAreaType);
}
