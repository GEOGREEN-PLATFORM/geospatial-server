package com.example.geospatialserver.service.impl;

import com.example.geospatialserver.model.dto.MarkerDTO;
import com.example.geospatialserver.service.GeospatialService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GeospatialServiceImpl implements GeospatialService {
    @Override
    public MarkerDTO createGeoPoint(MarkerDTO marker) {
        return null;
    }

    @Override
    public MarkerDTO getGeoPoint(UUID geoPontId) {
        return null;
    }

    @Override
    public MarkerDTO updateGeoPoint(MarkerDTO marker) {
        return null;
    }

    @Override
    public void deleteGeoPoint(UUID geoPointId) {

    }

    @Override
    public List<MarkerDTO> getAllGeoPoints(String problemAreaType) {
        return List.of();
    }
}
