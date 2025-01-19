package com.example.geospatialserver.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "geospatial")
public class GeoPointEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "x_coordinate", nullable = false)
    private Float xCoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private Float yCoordinate;

    @Column(name = "square")
    private Float square;

    @Column(name = "owner", nullable = false)
    private String owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "land_type_id", referencedColumnName = "id", nullable = false)
    private LandTypeEntity landTypeEntity;

    @Column(name = "contracting_organization", nullable = false)
    private String contractingOrganization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_stage_id", referencedColumnName = "id", nullable = false)
    private WorkStageEntity workStageEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_area_type_id", referencedColumnName = "id", nullable = false)
    private EliminationMethodEntity eliminationMethodEntity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "images")
    private List<UUID> images;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "elimination_method_id", referencedColumnName = "id", nullable = false)
    private ProblemAreaTypeEntity problemAreaType;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "related_task_id", nullable = false)
    private UUID relatedTaskId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "coordinates", nullable = false)
    private List<List<Float>> coordinates;
}
