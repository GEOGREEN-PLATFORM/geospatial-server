package com.example.geospatialserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/geo/dict")
public interface DictController {
    @GetMapping(path = "/problem-area-types")
    ResponseEntity<List<String>> getProblemAreaTypes();

    @GetMapping(path = "/elimination-methods/{problem-area-type}")
    ResponseEntity<List<String>> getEliminationMethods(@PathVariable String problemAreaTypeName);

    @GetMapping(path = "/land-types")
    ResponseEntity<List<String>> getLandTypes();

    @GetMapping(path = "/work-stages")
    ResponseEntity<List<String>> getWorkStages();
}
