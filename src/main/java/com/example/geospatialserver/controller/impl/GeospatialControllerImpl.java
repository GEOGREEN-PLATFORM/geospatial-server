package com.example.geospatialserver.controller.impl;

import com.example.geospatialserver.controller.GeospatialController;
import com.example.geospatialserver.model.dto.MarkerDTO;
import com.example.geospatialserver.service.GeospatialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GeospatialControllerImpl implements GeospatialController {
    private final GeospatialService geospatialService;

    @Override
    public ResponseEntity<MarkerDTO> createGeoPoint(MarkerDTO markerDTO) {
        return ResponseEntity.ok(geospatialService.createGeoPoint(markerDTO));
    }

    @Override
    public ResponseEntity<MarkerDTO> getGeoPoint(UUID geoPontId) {
        return ResponseEntity.ok(geospatialService.getGeoPoint(geoPontId));
    }

    @Override
    public ResponseEntity<MarkerDTO> updateGeoPoint(MarkerDTO markerDTO) {
        return ResponseEntity.ok(geospatialService.updateGeoPoint(markerDTO));
    }

    @Override
    public ResponseEntity<Void> deleteGeoPoint(UUID geoPontId) {
        geospatialService.deleteGeoPoint(geoPontId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MarkerDTO>> getAllGeoPoints(String problemAreaType) {
        return ResponseEntity.ok(geospatialService.getAllGeoPoints(problemAreaType));
    }
}
