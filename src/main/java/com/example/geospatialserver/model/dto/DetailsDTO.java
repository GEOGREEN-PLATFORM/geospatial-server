package com.example.geospatialserver.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsDTO {
    private Double square;

    @NotNull
    private String owner;

    @NotNull
    private String landType;

    @NotNull
    private String contractingOrganization;

    @NotNull
    private String workStage;

    @NotNull
    private String eliminationMethod;

    private List<ImageUrlDTO> images;

    private String problemAreaType;

    private String comment;

    private String density;
}
