package com.example.geospatial_server.controller.impl;

import com.example.geospatial_server.controller.GeospatialController;
import com.example.geospatial_server.model.dto.MarkerDTO;
import com.example.geospatial_server.model.dto.enums.ProblemAreaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class GeospatialControllerImpl implements GeospatialController {
    @Override
    public ResponseEntity<MarkerDTO> getGeoPointInfo(UUID geoPontId) {
        MarkerDTO dto = new MarkerDTO();
        dto.setCoordinates(null );
        dto.setId(UUID.randomUUID());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<MarkerDTO> saveGeoPointInfo(MarkerDTO markerDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<MarkerDTO>> getAllGeoPointsByProblemAreaType(ProblemAreaType problemAreaType) {
        return null;
    }
}
