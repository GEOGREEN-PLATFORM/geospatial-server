package com.example.geospatial_server.controller;


import com.example.geospatial_server.controller.impl.DictControllerImpl;
import com.example.geospatial_server.service.DictService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DictControllerImplTest {

    @Mock
    private DictService dictService;

    @InjectMocks
    private DictControllerImpl controller;

    @Test
    void getProblemAreaTypes_ReturnsOkAndBody() {
        List<String> types = List.of("Type1", "Type2");
        when(dictService.getProblemAreaTypes()).thenReturn(types);

        ResponseEntity<List<String>> response = controller.getProblemAreaTypes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(types, response.getBody());
        verify(dictService).getProblemAreaTypes();
    }

    @Test
    void getEliminationMethods_ReturnsOkAndBody() {
        String problemType = "TypeA";
        List<String> methods = List.of("Method1", "Method2");
        when(dictService.getEliminationMethods(problemType)).thenReturn(methods);

        ResponseEntity<List<String>> response = controller.getEliminationMethods(problemType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(methods, response.getBody());
        verify(dictService).getEliminationMethods(problemType);
    }

    @Test
    void getEliminationMethods_WhenServiceThrowsException_Propagates() {
        String problemType = "Unknown";
        when(dictService.getEliminationMethods(problemType))
                .thenThrow(new EntityNotFoundException("Not found"));

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> controller.getEliminationMethods(problemType)
        );
        assertEquals("Not found", ex.getMessage());
        verify(dictService).getEliminationMethods(problemType);
    }

    @Test
    void getLandTypes_ReturnsOkAndBody() {
        List<String> lands = List.of("Land1", "Land2");
        when(dictService.getLandTypes()).thenReturn(lands);

        ResponseEntity<List<String>> response = controller.getLandTypes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(lands, response.getBody());
        verify(dictService).getLandTypes();
    }

    @Test
    void getWorkStages_ReturnsOkAndBody() {
        List<String> stages = List.of("Stage1", "Stage2");
        when(dictService.getWorkStages()).thenReturn(stages);

        ResponseEntity<List<String>> response = controller.getWorkStages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(stages, response.getBody());
        verify(dictService).getWorkStages();
    }
}