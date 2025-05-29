package com.example.geospatial_server.mapper;


import com.example.geospatial_server.mappers.GeoPointMapper;
import com.example.geospatial_server.model.dto.Density;
import com.example.geospatial_server.model.dto.DetailsDTO;
import com.example.geospatial_server.model.dto.ImageUrlDTO;
import com.example.geospatial_server.model.dto.MarkerDTO;
import com.example.geospatial_server.model.entity.EliminationMethodEntity;
import com.example.geospatial_server.model.entity.GeoPointEntity;
import com.example.geospatial_server.model.entity.LandTypeEntity;
import com.example.geospatial_server.model.entity.ProblemAreaTypeEntity;
import com.example.geospatial_server.model.entity.WorkStageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GeoPointMapperTest {

    private GeoPointMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(GeoPointMapper.class);
    }

    @Test
    void toEntity_MapsMarkerDtoToGeoPointEntity() {
        UUID opId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        ImageUrlDTO img1 = new ImageUrlDTO(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")
        );
        MarkerDTO dto = new MarkerDTO();
        dto.setCoordinate(List.of(1.0, 2.0));
        DetailsDTO details = new DetailsDTO();
        details.setSquare(0.5);
        details.setOwner("owner1");
        details.setLandType("Forest");
        details.setContractingOrganization("org1");
        details.setWorkStage("CREATED");
        details.setEliminationMethod("MethodA");
        details.setImages(List.of(img1));
        details.setProblemAreaType("TypeA");
        details.setComment("comment");
        details.setDensity(Density.LOW);
        details.setCreationDate(OffsetDateTime.parse("2025-05-24T12:34:56.789Z"));
        details.setUpdateDate(OffsetDateTime.parse("2025-05-24T12:34:56.789Z"));
        details.setOperatorId(opId);
        dto.setDetails(details);

        GeoPointEntity actual = mapper.toEntity(dto);

        GeoPointEntity expected = new GeoPointEntity();
        expected.setXCoordinate(1.0);
        expected.setYCoordinate(2.0);
        expected.setSquare(0.5);
        expected.setOwner("owner1");
        expected.setContractingOrganization("org1");
        expected.setImages(List.of(img1));
        expected.setComment("comment");
        expected.setDensity(Density.LOW);
        expected.setOperatorId(opId);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id",
                        "landType",
                        "workStage",
                        "eliminationMethod",
                        "problemAreaType",
                        "relatedTaskIds",
                        "coordinates",
                        "creationDate",
                        "updateDate"
                )
                .isEqualTo(expected);
    }

    @Test
    void toDTO_MapsGeoPointEntityToMarkerDto() {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID opId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID rt1 = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID rt2 = UUID.fromString("44444444-4444-4444-4444-444444444444");
        OffsetDateTime created = OffsetDateTime.parse("2025-05-24T12:34:56.789Z");
        OffsetDateTime updated = OffsetDateTime.parse("2025-05-24T12:34:56.789Z");
        ImageUrlDTO img1 = new ImageUrlDTO(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")
        );

        LandTypeEntity lt = LandTypeEntity.builder().id(1).name("Forest").build();
        WorkStageEntity ws = WorkStageEntity.builder().id(2).name("CREATED").build();
        EliminationMethodEntity em =
                EliminationMethodEntity.builder()
                        .id(3).name("MethodA").problemAreaTypeId(4).build();
        ProblemAreaTypeEntity pat =
                ProblemAreaTypeEntity.builder().id(4).name("TypeA").build();

        GeoPointEntity ent = GeoPointEntity.builder()
                .id(id)
                .xCoordinate(1.0)
                .yCoordinate(2.0)
                .square(0.5)
                .owner("owner1")
                .landType(lt)
                .contractingOrganization("org1")
                .workStage(ws)
                .eliminationMethod(em)
                .images(List.of(img1))
                .problemAreaType(pat)
                .comment("comment")
                .relatedTaskIds(List.of(rt1, rt2))
                .coordinates(List.of(
                        List.of(0.0, 0.0),
                        List.of(1.0, 0.0),
                        List.of(0.0, 1.0)
                ))
                .density(Density.LOW)
                .creationDate(created)
                .updateDate(updated)
                .operatorId(opId)
                .build();

        MarkerDTO actual = mapper.toDTO(ent);

        DetailsDTO expectedDetails = new DetailsDTO();
        expectedDetails.setSquare(0.5);
        expectedDetails.setOwner("owner1");
        expectedDetails.setLandType("Forest");
        expectedDetails.setContractingOrganization("org1");
        expectedDetails.setWorkStage("CREATED");
        expectedDetails.setEliminationMethod("MethodA");
        expectedDetails.setImages(List.of(img1));
        expectedDetails.setProblemAreaType("TypeA");
        expectedDetails.setComment("comment");
        expectedDetails.setDensity(Density.LOW);
        expectedDetails.setCreationDate(created);
        expectedDetails.setUpdateDate(updated);
        expectedDetails.setOperatorId(opId);

        MarkerDTO expected = new MarkerDTO();
        expected.setId(id);
        expected.setCoordinate(List.of(1.0, 2.0));
        expected.setDetails(expectedDetails);
        expected.setRelatedTaskIds(List.of(rt1, rt2));
        expected.setCoordinates(List.of(
                List.of(0.0, 0.0),
                List.of(1.0, 0.0),
                List.of(0.0, 1.0)
        ));

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields()
                .isEqualTo(expected);
    }

    @Test
    void mergeGeoPoint_OverridesOnlyNonNullFields() {
        GeoPointEntity ent = new GeoPointEntity();
        ent.setXCoordinate(0.0);
        ent.setYCoordinate(0.0);
        ent.setOwner("oldOwner");
        ent.setContractingOrganization("oldOrg");
        ent.setImages(List.of());
        ent.setComment("oldComment");
        ent.setDensity(Density.MIDDLE);
        ent.setOperatorId(UUID.fromString("55555555-5555-5555-5555-555555555555"));
        ent.setRelatedTaskIds(List.of(UUID.fromString("66666666-6666-6666-6666-666666666666")));
        ent.setCoordinates(List.of(List.of(9.9, 9.9)));
        ent.setWorkStage(new WorkStageEntity(1, "done"));

        MarkerDTO dto = new MarkerDTO();
        dto.setCoordinate(List.of(11.0, 22.0));
        dto.setRelatedTaskIds(List.of(
                UUID.fromString("77777777-7777-7777-7777-777777777777"),
                UUID.fromString("88888888-8888-8888-8888-888888888888")
        ));
        dto.setCoordinates(List.of(List.of(1.1, 1.2)));
        DetailsDTO details = new DetailsDTO();
        details.setOwner("newOwner");
        details.setContractingOrganization(null);
        ImageUrlDTO newImg = new ImageUrlDTO(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")
        );
        details.setImages(List.of(newImg));
        details.setComment(null);
        details.setDensity(Density.HIGH);
        details.setOperatorId(UUID.fromString("99999999-9999-9999-9999-999999999999"));
        details.setWorkStage("done");
        dto.setDetails(details);

        GeoPointEntity merged = mapper.mergeGeoPoint(ent, dto);

        assertThat(merged.getXCoordinate()).isEqualTo(11.0);
        assertThat(merged.getYCoordinate()).isEqualTo(22.0);
        assertThat(merged.getRelatedTaskIds())
                .containsExactly(
                        UUID.fromString("77777777-7777-7777-7777-777777777777"),
                        UUID.fromString("88888888-8888-8888-8888-888888888888")
                );
        assertThat(merged.getCoordinates()).containsExactly(List.of(1.1, 1.2));
        assertThat(merged.getOwner()).isEqualTo("newOwner");
        assertThat(merged.getContractingOrganization()).isEqualTo("oldOrg");
        assertThat(merged.getImages()).containsExactly(newImg);
        assertThat(merged.getComment()).isEqualTo("oldComment");
        assertThat(merged.getDensity()).isEqualTo(Density.HIGH);
        assertThat(merged.getOperatorId())
                .isEqualTo(UUID.fromString("99999999-9999-9999-9999-999999999999"));
    }

    @Test
    void mergeGeoPoint_NullDetailsAndLists_NoChange() {
        GeoPointEntity ent = new GeoPointEntity();
        ent.setXCoordinate(5.0);
        ent.setYCoordinate(6.0);
        ent.setOwner("ownerA");
        ent.setContractingOrganization("orgA");
        ent.setImages(List.of(new ImageUrlDTO(UUID.fromString("aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1"), UUID.fromString("bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1"))));
        ent.setComment("commentA");
        ent.setDensity(Density.MIDDLE);
        ent.setOperatorId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        ent.setRelatedTaskIds(List.of(UUID.fromString("22222222-2222-2222-2222-222222222222")));
        ent.setCoordinates(List.of(List.of(0.0, 0.0)));

        MarkerDTO dto = new MarkerDTO();
        dto.setCoordinate(null);
        dto.setDetails(null);
        dto.setRelatedTaskIds(null);
        dto.setCoordinates(null);

        GeoPointEntity merged = mapper.mergeGeoPoint(ent, dto);
        assertThat(merged).isSameAs(ent);
    }
}