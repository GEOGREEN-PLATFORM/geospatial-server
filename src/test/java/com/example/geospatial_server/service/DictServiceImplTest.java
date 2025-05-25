package com.example.geospatial_server.service;

import com.example.geospatial_server.model.entity.EliminationMethodEntity;
import com.example.geospatial_server.model.entity.LandTypeEntity;
import com.example.geospatial_server.model.entity.ProblemAreaTypeEntity;
import com.example.geospatial_server.model.entity.WorkStageEntity;
import com.example.geospatial_server.repository.LandTypeRepository;
import com.example.geospatial_server.repository.ProblemAreaTypeRepository;
import com.example.geospatial_server.repository.WorkStageRepository;
import com.example.geospatial_server.service.impl.DictServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DictServiceImplTest {

    @Mock
    private ProblemAreaTypeRepository problemAreaTypeRepository;

    @Mock
    private LandTypeRepository landTypeRepository;

    @Mock
    private WorkStageRepository workStageRepository;

    @InjectMocks
    private DictServiceImpl dictService;

    private ProblemAreaTypeEntity problemAreaTypeA;
    private EliminationMethodEntity method1;
    private EliminationMethodEntity method2;

    @BeforeEach
    void setUp() {
        method1 = EliminationMethodEntity.builder()
                .id(1)
                .name("Method A")
                .problemAreaTypeId(10)
                .build();
        method2 = EliminationMethodEntity.builder()
                .id(2)
                .name("Method B")
                .problemAreaTypeId(10)
                .build();

        problemAreaTypeA = ProblemAreaTypeEntity.builder()
                .id(10)
                .name("Type A")
                .eliminationMethodEntities(List.of(method1, method2))
                .build();
    }

    @Test
    void getProblemAreaTypes_ReturnsAllNames() {
        when(problemAreaTypeRepository.findAll())
                .thenReturn(List.of(
                        ProblemAreaTypeEntity.builder().id(1).name("A").build(),
                        ProblemAreaTypeEntity.builder().id(2).name("B").build()
                ));

        List<String> names = dictService.getProblemAreaTypes();

        assertEquals(2, names.size());
        assertTrue(names.containsAll(List.of("A", "B")));

        verify(problemAreaTypeRepository, times(1)).findAll();
    }

    @Test
    void getEliminationMethods_ExistingType_ReturnsMethodNames() {
        when(problemAreaTypeRepository.findByName("Type A"))
                .thenReturn(Optional.of(problemAreaTypeA));

        List<String> methods = dictService.getEliminationMethods("Type A");

        assertEquals(2, methods.size());
        assertTrue(methods.containsAll(List.of("Method A", "Method B")));

        verify(problemAreaTypeRepository, times(1)).findByName("Type A");
    }

    @Test
    void getEliminationMethods_NonExistingType_ThrowsException() {
        when(problemAreaTypeRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, ()
                -> dictService.getEliminationMethods("Unknown"));

        assertEquals("Неразрешённый тип проблемы", ex.getMessage());
        verify(problemAreaTypeRepository, times(1)).findByName("Unknown");
    }

    @Test
    void getLandTypes_ReturnsAllNames() {
        when(landTypeRepository.findAll())
                .thenReturn(List.of(
                        LandTypeEntity.builder().id(1).name("Land X").build(),
                        LandTypeEntity.builder().id(2).name("Land Y").build()
                ));

        List<String> lands = dictService.getLandTypes();

        assertEquals(2, lands.size());
        assertTrue(lands.containsAll(List.of("Land X", "Land Y")));

        verify(landTypeRepository, times(1)).findAll();
    }

    @Test
    void getWorkStages_ReturnsAllNames() {
        when(workStageRepository.findAll())
                .thenReturn(List.of(
                        WorkStageEntity.builder().id(1).name("Stage 1").build(),
                        WorkStageEntity.builder().id(2).name("Stage 2").build()
                ));

        List<String> stages = dictService.getWorkStages();

        assertEquals(2, stages.size());
        assertTrue(stages.containsAll(List.of("Stage 1", "Stage 2")));

        verify(workStageRepository, times(1)).findAll();
    }
}