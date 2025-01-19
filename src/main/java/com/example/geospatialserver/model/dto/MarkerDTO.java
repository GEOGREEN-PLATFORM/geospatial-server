package com.example.geospatialserver.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkerDTO {
    @NotNull
    private UUID id;

    //@Size(min = 2, max = 2)?
    @NotNull
    private Double[] coordinate;

    @NotNull
    @Valid
    private DetailsDTO details;

    private UUID relatedTaskId;

    private Double[][] coordinates;
}
