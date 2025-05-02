package com.example.geospatialserver.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListMarkerResponse {
    private List<MarkerDTO> geoPoints;
    private int currentPage;
    private long totalItems;
    private int totalPages;
}