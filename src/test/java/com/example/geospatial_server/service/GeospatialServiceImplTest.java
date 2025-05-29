package com.example.geospatial_server.service;


import com.example.geospatial_server.mappers.GeoPointMapper;
import com.example.geospatial_server.model.dto.Density;
import com.example.geospatial_server.model.dto.DetailsDTO;
import com.example.geospatial_server.model.dto.ListMarkerResponse;
import com.example.geospatial_server.model.dto.MarkerDTO;
import com.example.geospatial_server.model.dto.OperatorStatisticDTO;
import com.example.geospatial_server.model.dto.RelatedTaskDTO;
import com.example.geospatial_server.model.entity.EliminationMethodEntity;
import com.example.geospatial_server.model.entity.GeoPointEntity;
import com.example.geospatial_server.model.entity.LandTypeEntity;
import com.example.geospatial_server.model.entity.ProblemAreaTypeEntity;
import com.example.geospatial_server.model.entity.WorkStageEntity;
import com.example.geospatial_server.model.kafka.Type;
import com.example.geospatial_server.model.kafka.UpdateElementDTO;
import com.example.geospatial_server.repository.EliminationMethodRepository;
import com.example.geospatial_server.repository.GeoPointRepository;
import com.example.geospatial_server.repository.LandTypeRepository;
import com.example.geospatial_server.repository.ProblemAreaTypeRepository;
import com.example.geospatial_server.repository.WorkStageRepository;
import com.example.geospatial_server.service.impl.GeospatialServiceImpl;
import com.example.geospatial_server.service.impl.KafkaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeospatialServiceImplTest {

    @Mock
    private GeoPointRepository geoPointRepository;
    @Mock
    private LandTypeRepository landTypeRepository;
    @Mock
    private WorkStageRepository workStageRepository;
    @Mock
    private EliminationMethodRepository eliminationMethodRepository;
    @Mock
    private ProblemAreaTypeRepository problemAreaTypeRepository;
    @Mock
    private GeoPointMapper geoPointMapper;
    @Mock
    private KafkaService kafkaService;

    @InjectMocks
    private GeospatialServiceImpl geospatialService;

    private LandTypeEntity landType;
    private WorkStageEntity workStage;
    private ProblemAreaTypeEntity problemAreaType;
    private EliminationMethodEntity eliminationMethod;
    private MarkerDTO markerDto;
    private GeoPointEntity geoPointEntity;

    @BeforeEach
    void setUp() {
        landType = LandTypeEntity.builder().id(1).name("Forest").build();
        workStage = WorkStageEntity.builder().id(2).name("CREATED").build();
        problemAreaType = ProblemAreaTypeEntity.builder().id(3).name("TypeA").build();
        eliminationMethod = EliminationMethodEntity.builder()
                .id(4).name("MethodA").problemAreaTypeId(3).build();

        DetailsDTO details = new DetailsDTO();
        details.setLandType("Forest");
        details.setWorkStage("CREATED");
        details.setProblemAreaType("TypeA");
        details.setEliminationMethod("MethodA");
        details.setOwner("owner1");
        details.setContractingOrganization("org1");
        details.setComment("comment");
        details.setDensity(Density.LOW);
        details.setOperatorId(UUID.randomUUID());

        markerDto = new MarkerDTO();
        markerDto.setCoordinate(List.of(1.0, 2.0));
        markerDto.setDetails(details);
        markerDto.setRelatedTaskIds(new ArrayList<>());
        List<List<Double>> coords = List.of(
                List.of(0.0, 0.0),
                List.of(1.0, 0.0),
                List.of(0.0, 1.0)
        );
        markerDto.setCoordinates(coords);

        geoPointEntity = new GeoPointEntity();
        geoPointEntity.setXCoordinate(1.0);
        geoPointEntity.setYCoordinate(2.0);
        geoPointEntity.setRelatedTaskIds(new ArrayList<>());
        geoPointEntity.setCoordinates(coords);
    }

    @Test
    void createGeoPoint_WithValidData_Success() {
        when(landTypeRepository.findByName("Forest")).thenReturn(Optional.of(landType));
        when(workStageRepository.findByName("CREATED")).thenReturn(Optional.of(workStage));
        when(problemAreaTypeRepository.findByName("TypeA")).thenReturn(Optional.of(problemAreaType));
        when(eliminationMethodRepository
                .findByProblemAreaTypeIdAndName(3, "MethodA"))
                .thenReturn(Optional.of(eliminationMethod));

        when(geoPointMapper.toEntity(markerDto)).thenReturn(geoPointEntity);
        when(geoPointRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(geoPointMapper.toDTO(geoPointEntity)).thenReturn(markerDto);

        MarkerDTO result = geospatialService.createGeoPoint(markerDto);

        assertSame(markerDto, result);
        ArgumentCaptor<GeoPointEntity> captor = ArgumentCaptor.forClass(GeoPointEntity.class);
        verify(geoPointRepository).save(captor.capture());

        assertEquals(0.5, captor.getValue().getSquare());
    }

    @Test
    void createGeoPoint_InvalidLandType_ThrowsException() {
        when(landTypeRepository.findByName("Forest")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> geospatialService.createGeoPoint(markerDto)
        );
        assertEquals("Неразрешённый тип земли", ex.getMessage());
    }

    @Test
    void createGeoPoint_TooFewCoordinates_ThrowsException() {
        markerDto.setCoordinates(List.of(
                List.of(0.0, 0.0),
                List.of(1.0, 0.0)
        ));
        when(landTypeRepository.findByName("Forest")).thenReturn(Optional.of(landType));
        when(workStageRepository.findByName("CREATED")).thenReturn(Optional.of(workStage));
        when(problemAreaTypeRepository.findByName("TypeA")).thenReturn(Optional.of(problemAreaType));
        when(eliminationMethodRepository
                .findByProblemAreaTypeIdAndName(3, "MethodA"))
                .thenReturn(Optional.of(eliminationMethod));
        when(geoPointMapper.toEntity(markerDto)).thenReturn(geoPointEntity);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> geospatialService.createGeoPoint(markerDto)
        );
        assertEquals("coordinates должены иметь как минимум 3 точки", ex.getMessage());
    }

    @Test
    void getGeoPoint_Exists_ReturnsDTO() {
        UUID id = UUID.randomUUID();
        geoPointEntity.setId(id);
        when(geoPointRepository.findById(id)).thenReturn(Optional.of(geoPointEntity));
        when(geoPointMapper.toDTO(geoPointEntity)).thenReturn(markerDto);

        MarkerDTO result = geospatialService.getGeoPoint(id);

        assertSame(markerDto, result);
    }

    @Test
    void getGeoPoint_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(geoPointRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> geospatialService.getGeoPoint(id)
        );
        assertTrue(ex.getMessage().contains(id.toString()));
    }

    @Test
    void updateGeoPoint_WorkStageChanged_SendsKafkaMessageAndUpdates() {
        UUID id = UUID.randomUUID();
        geoPointEntity.setId(id);
        geoPointEntity.setWorkStage(workStage);

        when(geoPointRepository.findById(id)).thenReturn(Optional.of(geoPointEntity));

        markerDto.getDetails().setWorkStage("PROCESSED");
        WorkStageEntity newStage = WorkStageEntity.builder().id(5).name("PROCESSED").build();
        when(workStageRepository.findByName("PROCESSED")).thenReturn(Optional.of(newStage));

        when(landTypeRepository.findByName("Forest")).thenReturn(Optional.of(landType));
        when(problemAreaTypeRepository.findByName("TypeA")).thenReturn(Optional.of(problemAreaType));
        when(eliminationMethodRepository
                .findByProblemAreaTypeIdAndName(problemAreaType.getId(), "MethodA"))
                .thenReturn(Optional.of(eliminationMethod));

        when(geoPointMapper.mergeGeoPoint(eq(geoPointEntity), eq(markerDto)))
                .thenReturn(geoPointEntity);
        when(geoPointRepository.saveAndFlush(geoPointEntity)).thenReturn(geoPointEntity);
        when(geoPointMapper.toDTO(geoPointEntity)).thenReturn(markerDto);

        MarkerDTO result = geospatialService.updateGeoPoint(id, markerDto);

        ArgumentCaptor<UpdateElementDTO> captor = ArgumentCaptor.forClass(UpdateElementDTO.class);
        verify(kafkaService).produceUpdateElementMessage(captor.capture());
        UpdateElementDTO msg = captor.getValue();
        assertEquals(id, msg.getElementId());
        assertEquals(Type.POINT.getName(), msg.getType());
        assertEquals("PROCESSED", msg.getStatus());

        verify(geoPointRepository).saveAndFlush(geoPointEntity);
        assertSame(markerDto, result);
    }

    @Test
    void deleteGeoPoint_Exists_DeletesSuccessfully() {
        UUID id = UUID.randomUUID();
        when(geoPointRepository.findById(id)).thenReturn(Optional.of(geoPointEntity));

        geospatialService.deleteGeoPoint(id);

        verify(geoPointRepository).deleteById(id);
    }

    @Test
    void deleteGeoPoint_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(geoPointRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> geospatialService.deleteGeoPoint(id));
    }

    @Test
    void getAllGeoPoints_NoFilter_ReturnsAll() {
        when(geoPointRepository.findAll()).thenReturn(List.of(geoPointEntity));
        when(geoPointMapper.toDTO(geoPointEntity)).thenReturn(markerDto);

        List<MarkerDTO> list = geospatialService.getAllGeoPoints(null);

        assertEquals(1, list.size());
        assertSame(markerDto, list.get(0));
    }

    @Test
    void getAllGeoPoints_FilterInvalidType_ThrowsException() {
        when(problemAreaTypeRepository.findByName("TypeA")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> geospatialService.getAllGeoPoints("TypeA"));
    }

    @Test
    void getAllGeoPoints_Filtered_ReturnsMatches() {
        when(problemAreaTypeRepository.findByName("TypeA")).thenReturn(Optional.of(problemAreaType));
        when(geoPointRepository.findByProblemAreaTypeId(3))
                .thenReturn(List.of(geoPointEntity));
        when(geoPointMapper.toDTO(geoPointEntity)).thenReturn(markerDto);

        List<MarkerDTO> list = geospatialService.getAllGeoPoints("TypeA");

        assertEquals(1, list.size());
        assertSame(markerDto, list.get(0));
    }

    @Test
    void getAllGeoPoints_Paginated_NoFilters() {
        Page<GeoPointEntity> page = new PageImpl<>(
                List.of(geoPointEntity),
                PageRequest.of(0, 1),
                1
        );
        when(geoPointRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(page);
        when(geoPointMapper.toDTO(geoPointEntity)).thenReturn(markerDto);

        ListMarkerResponse resp = geospatialService.getAllGeoPoints(
                0, 1, null, null, null, null, null, null, null, null
        );

        assertEquals(0, resp.getCurrentPage());
        assertEquals(1, resp.getTotalItems());
        assertEquals(1, resp.getTotalPages());
        assertEquals(1, resp.getGeoPoints().size());
    }

    @Test
    void addRelatedTask_AppendsIdAndSaves() {
        //Given
        geoPointEntity = new GeoPointEntity();
        geoPointEntity.setXCoordinate(1.0);
        geoPointEntity.setYCoordinate(2.0);
        geoPointEntity.setRelatedTaskIds(new ArrayList<>());
        UUID id = UUID.randomUUID();
        RelatedTaskDTO req = new RelatedTaskDTO();
        UUID taskId = UUID.randomUUID();
        req.setRelatedTaskId(taskId);

        when(geoPointRepository.findById(id)).thenReturn(Optional.of(geoPointEntity));

        //when
        geospatialService.addRelatedTask(id, req);

        //then
        assertTrue(geoPointEntity.getRelatedTaskIds().contains(taskId));
        verify(geoPointRepository).save(geoPointEntity);
    }

    @Test
    void getStatistic_CountsByWorkStage() {
        UUID operatorId = UUID.randomUUID();
        WorkStageEntity created = WorkStageEntity.builder().id(2).name("Создано").build();
        WorkStageEntity processed = WorkStageEntity.builder().id(5).name("В работе").build();

        GeoPointEntity e1 = new GeoPointEntity();
        e1.setWorkStage(created);
        GeoPointEntity e2 = new GeoPointEntity();
        e2.setWorkStage(processed);
        GeoPointEntity e3 = new GeoPointEntity();
        e3.setWorkStage(created);

        when(geoPointRepository.findByOperatorId(operatorId))
                .thenReturn(List.of(e1, e2, e3));

        OperatorStatisticDTO stats = geospatialService.getStatistic(operatorId);

        assertEquals(2L, stats.getCreatedGeoPoints());
        assertEquals(1L, stats.getProcessedGeoPoints());
        assertNull(stats.getClosedGeoPoints());
    }
}