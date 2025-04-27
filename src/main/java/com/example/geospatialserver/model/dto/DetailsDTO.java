package com.example.geospatialserver.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

import static com.example.geospatialserver.util.DateUtil.ISO_8601_DATE_TIME_MILLIS_PATTERN;
import static com.example.geospatialserver.util.DateUtil.UTC;

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

    private Density density;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = ISO_8601_DATE_TIME_MILLIS_PATTERN,
            timezone = UTC
    )
    private OffsetDateTime creationDate;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = ISO_8601_DATE_TIME_MILLIS_PATTERN,
            timezone = UTC
    )
    private OffsetDateTime updateDate;
}