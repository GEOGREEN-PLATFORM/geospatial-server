package com.example.geospatialserver.controller.impl;

import com.example.geospatialserver.controller.DictController;
import com.example.geospatialserver.service.DictService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.geospatialserver.util.AuthorizationStringUtil.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = AUTHORIZATION)
public class DictControllerImpl implements DictController {
    private final DictService dictService;

    @Override
    public ResponseEntity<List<String>> getProblemAreaTypes() {
        return ResponseEntity.ok(dictService.getProblemAreaTypes());
    }

    @Override
    public ResponseEntity<List<String>> getEliminationMethods(String problemAreaTypeName) {
        return ResponseEntity.ok(dictService.getEliminationMethods(problemAreaTypeName));
    }

    @Override
    public ResponseEntity<List<String>> getLandTypes() {
        return ResponseEntity.ok(dictService.getLandTypes());
    }

    @Override
    public ResponseEntity<List<String>> getWorkStages() {
        return ResponseEntity.ok(dictService.getWorkStages());
    }
}
