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
    @Mapping(target = "contractingOrganization", source = "details.contractingOrganization")
    @Mapping(target = "images", source = "details.images")
    @Mapping(target = "comment", source = "details.comment")
    @Mapping(target = "density", source = "details.density")
    @Mapping(target = "operatorId", source = "details.operatorId")
    GeoPointEntity toEntity(MarkerDTO markerDTO);

    @Mapping(target = "coordinate", expression = "java(List.of(geoPointEntity.getXCoordinate(), geoPointEntity.getYCoordinate()))")
    @Mapping(target = "details.square", source = "square")
    @Mapping(target = "details.owner", source = "owner")
    @Mapping(target = "details.contractingOrganization", source = "contractingOrganization")
    @Mapping(target = "details.images", source = "images")
    @Mapping(target = "details.comment", source = "comment")
    @Mapping(target = "details.landType", source = "landType.name")
    @Mapping(target = "details.workStage", source = "workStage.name")
    @Mapping(target = "details.eliminationMethod", source = "eliminationMethod.name")
    @Mapping(target = "details.problemAreaType", source = "problemAreaType.name")
    @Mapping(target = "details.density", source = "density")
    @Mapping(target = "details.creationDate", source = "creationDate")
    @Mapping(target = "details.updateDate", source = "updateDate")
    @Mapping(target = "details.operatorId", source = "operatorId")
    MarkerDTO toDTO(GeoPointEntity geoPointEntity);

    default GeoPointEntity mergeGeoPoint(GeoPointEntity entity, MarkerDTO dto) {
        if (dto.getCoordinate() != null) {
            entity.setXCoordinate(dto.getCoordinate().get(0));
            entity.setYCoordinate(dto.getCoordinate().get(1));
        }
        if (dto.getRelatedTaskIds() != null) entity.setRelatedTaskIds(dto.getRelatedTaskIds());
        if (dto.getCoordinates() != null) entity.setCoordinates(dto.getCoordinates());
        if (dto.getDetails() != null) {
            if (dto.getDetails().getOwner() != null) entity.setOwner(dto.getDetails().getOwner());
            if (dto.getDetails().getContractingOrganization() != null)
                entity.setContractingOrganization(dto.getDetails().getContractingOrganization());
            if (dto.getDetails().getImages() != null) entity.setImages(dto.getDetails().getImages());
            if (dto.getDetails().getComment() != null) entity.setComment(dto.getDetails().getComment());
            if (dto.getDetails().getDensity() != null) entity.setDensity(dto.getDetails().getDensity());
            if (dto.getDetails().getOperatorId() != null) entity.setOperatorId(dto.getDetails().getOperatorId());
        }
        return entity;
    }
}