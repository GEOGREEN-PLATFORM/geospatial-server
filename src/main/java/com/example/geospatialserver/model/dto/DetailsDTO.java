package com.example.geospatialserver.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private String landType;

    @NotNull
    private String contractingOrganization;

    @NotNull
    private String workStage;

    private String eliminationMethod;

    @Size(max = 10, message = "Количество изображений не может быть больше 10")
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