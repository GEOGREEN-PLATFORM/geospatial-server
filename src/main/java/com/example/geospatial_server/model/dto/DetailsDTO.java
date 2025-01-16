package com.example.geospatial_server.model.dto;

import com.example.geospatial_server.model.dto.enums.LandType;
import com.example.geospatial_server.model.dto.enums.ProblemAreaType;
import com.example.geospatial_server.model.dto.enums.EliminationMethod;
import com.example.geospatial_server.model.dto.enums.WorkStage;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DetailsDTO {
    private Double square;
    private String owner;
    private LandType landType;
    private String contractingOrganization;
    private WorkStage workStage;
    private EliminationMethod eliminationMethod;
    private List<UUID> images;
    private ProblemAreaType type;
    private String comment;
}
