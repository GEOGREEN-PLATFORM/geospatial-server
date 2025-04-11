package com.example.geospatialserver.mappers;

import com.example.geospatialserver.model.dto.MarkerDTO;
import com.example.geospatialserver.model.entity.GeoPointEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GeoPointMapper {
    @Mapping(target = "xCoordinate", expression = "java(markerDTO.getCoordinate().get(0))")
    @Mapping(target = "yCoordinate", expression = "java(markerDTO.getCoordinate().get(1))")
    @Mapping(target = "square", source = "details.square")
    @Mapping(target = "owner", source = "details.owner")
    @Mapping(target = "contractingOrganization", source = "markerDTO.details.contractingOrganization")
    @Mapping(target = "images", source = "markerDTO.details.images")
    @Mapping(target = "comment", source = "markerDTO.details.comment")
    GeoPointEntity toEntity(MarkerDTO markerDTO);

    @Mapping(target = "coordinate", expression = "java(List.of(geoPointEntity.getXCoordinate(), geoPointEntity.getYCoordinate()))")
    @Mapping(target = "details.square", source = "square")
    @Mapping(target = "details.owner", source = "owner")
    @Mapping(target = "details.contractingOrganization", source = "contractingOrganization")
    @Mapping(target = "details.images", source = "images")
    @Mapping(target = "details.comment", source = "comment")
    @Mapping(target = "details.landType", expression = "java(geoPointEntity.getLandType().getName())")
    @Mapping(target = "details.workStage", expression = "java(geoPointEntity.getWorkStage().getName())")
    @Mapping(target = "details.eliminationMethod", expression = "java(geoPointEntity.getEliminationMethod().getName())")
    @Mapping(target = "details.problemAreaType", expression = "java(geoPointEntity.getProblemAreaType().getName())")
    MarkerDTO toDTO(GeoPointEntity geoPointEntity);

    default GeoPointEntity mergeGeoPoint(GeoPointEntity entity, MarkerDTO dto) {
        if (dto.getCoordinate() != null) {
            entity.setXCoordinate(dto.getCoordinate().get(0));
            entity.setYCoordinate(dto.getCoordinate().get(1));
        }
        if (dto.getRelatedTaskId() != null) entity.setRelatedTaskId(dto.getRelatedTaskId());
        if (dto.getCoordinates() != null) entity.setCoordinates(dto.getCoordinates());
        if (dto.getDetails() != null) {
            if (dto.getDetails().getOwner() != null) entity.setOwner(dto.getDetails().getOwner());
            if (dto.getDetails().getContractingOrganization() != null)
                entity.setContractingOrganization(dto.getDetails().getContractingOrganization());
            if (dto.getDetails().getImages() != null) entity.setImages(dto.getDetails().getImages());
            if (dto.getDetails().getComment() != null) entity.setComment(dto.getDetails().getComment());
            if (dto.getDetails().getDensity() != null) entity.setDensity(dto.getDetails().getDensity());
        }
        return entity;
    }
}
