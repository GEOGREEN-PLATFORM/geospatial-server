package com.example.geospatialserver.controller.impl;

import com.example.geospatialserver.controller.GeospatialController;
import com.example.geospatialserver.model.dto.MarkerDTO;
import com.example.geospatialserver.service.GeospatialService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.example.geospatialserver.util.AuthorizationStringUtil.ADMIN;
import static com.example.geospatialserver.util.AuthorizationStringUtil.AUTHORIZATION;
import static com.example.geospatialserver.util.AuthorizationStringUtil.OPERATOR;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = AUTHORIZATION)
public class GeospatialControllerImpl implements GeospatialController {
    private final GeospatialService geospatialService;

    @RolesAllowed({ADMIN, OPERATOR})
    @PostMapping
    @Override
    public ResponseEntity<MarkerDTO> createGeoPoint(MarkerDTO markerDTO) {
        return ResponseEntity.ok(geospatialService.createGeoPoint(markerDTO));
    }

    @Override
    public ResponseEntity<MarkerDTO> getGeoPoint(UUID geoPontId) {
        return ResponseEntity.ok(geospatialService.getGeoPoint(geoPontId));
    }

    @RolesAllowed({ADMIN, OPERATOR})
    @Override
    public ResponseEntity<MarkerDTO> updateGeoPoint(UUID geoPontId, MarkerDTO markerDTO) {
        return ResponseEntity.ok(geospatialService.updateGeoPoint(geoPontId, markerDTO));
    }

    @RolesAllowed({ADMIN, OPERATOR})
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
