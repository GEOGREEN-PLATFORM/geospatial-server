package com.example.geospatialserver.controller.impl;

import com.example.geospatialserver.controller.DictController;
import com.example.geospatialserver.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
