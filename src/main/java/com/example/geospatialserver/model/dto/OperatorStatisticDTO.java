package com.example.geospatialserver.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperatorStatisticDTO {
    private Long createdGeoPoints;
    private Long processedGeoPoints;
    private Long closedGeoPoints;
}