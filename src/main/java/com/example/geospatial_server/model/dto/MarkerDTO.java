package com.example.geospatial_server.model.dto;

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
    private CoordinateDTO coordinate;
    private DetailsDTO details;
    private UUID relatedTaskId;
    private List<CoordinateDTO> coordinates;
}
