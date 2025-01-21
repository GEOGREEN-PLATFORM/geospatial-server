package com.example.geospatialserver.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkerDTO {
    private UUID id;

    @Size(min = 2, max = 2)
    @NotNull
    private List<Double> coordinate;

    @NotNull
    @Valid
    private DetailsDTO details;

    private UUID relatedTaskId;

    private List<List<Double>> coordinates;
}
