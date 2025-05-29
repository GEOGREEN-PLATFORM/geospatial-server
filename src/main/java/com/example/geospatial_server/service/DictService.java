package com.example.geospatial_server.service;

import java.util.List;

public interface DictService {
    List<String> getProblemAreaTypes();

    List<String> getEliminationMethods(String problemAreaTypeName);

    List<String> getLandTypes();

    List<String> getWorkStages();
}
