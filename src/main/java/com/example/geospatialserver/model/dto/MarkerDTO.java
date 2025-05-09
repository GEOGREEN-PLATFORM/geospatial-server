package com.example.geospatialserver.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
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

    @Builder.Default
    private List<UUID> relatedTaskIds = new ArrayList<>();
    ;

    private List<List<Double>> coordinates;
}
