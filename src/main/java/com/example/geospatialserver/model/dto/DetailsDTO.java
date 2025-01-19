package com.example.geospatialserver.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DetailsDTO {
    private Double square;

    @NotNull
    private String owner;

    //todo проверить если такой тип в бд
    @NotNull
    private String landType;

    @NotNull
    private String contractingOrganization;

    //todo проверить если такой тип в бд
    @NotNull
    private String workStage;

    //todo проверить если такой тип в бд
    @NotNull
    private String eliminationMethod;

    private List<UUID> images;

    //todo проверить если такой тип в бд
    private String problemAreaType;

    private String comment;
}
