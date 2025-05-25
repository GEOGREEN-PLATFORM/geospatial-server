package com.example.geospatial_server.controller;


import com.example.geospatial_server.controller.impl.GeospatialControllerImpl;
import com.example.geospatial_server.model.dto.Density;
import com.example.geospatial_server.model.dto.ListMarkerResponse;
import com.example.geospatial_server.model.dto.MarkerDTO;
import com.example.geospatial_server.model.dto.OperatorStatisticDTO;
import com.example.geospatial_server.model.dto.RelatedTaskDTO;
import com.example.geospatial_server.service.GeospatialService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeospatialControllerImplTest {

    @Mock
    private GeospatialService geospatialService;

    @InjectMocks
    private GeospatialControllerImpl controller;

    @Test
    void createGeoPoint_ReturnsOkAndBody() {
        MarkerDTO dto = new MarkerDTO();
        UUID id = UUID.randomUUID();
        dto.setId(id);

        when(geospatialService.createGeoPoint(dto)).thenReturn(dto);

        ResponseEntity<MarkerDTO> resp = controller.createGeoPoint(dto);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertSame(dto, resp.getBody());
        verify(geospatialService).createGeoPoint(dto);
    }

    @Test
    void getGeoPoint_ReturnsOkAndBody() {
        UUID id = UUID.randomUUID();
        MarkerDTO dto = new MarkerDTO();
        dto.setId(id);

        when(geospatialService.getGeoPoint(id)).thenReturn(dto);

        ResponseEntity<MarkerDTO> resp = controller.getGeoPoint(id);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertSame(dto, resp.getBody());
        verify(geospatialService).getGeoPoint(id);
    }

    @Test
    void updateGeoPoint_ReturnsOkAndBody() {
        UUID id = UUID.randomUUID();
        MarkerDTO dto = new MarkerDTO();
        dto.setId(id);

        when(geospatialService.updateGeoPoint(id, dto)).thenReturn(dto);

        ResponseEntity<MarkerDTO> resp = controller.updateGeoPoint(id, dto);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertSame(dto, resp.getBody());
        verify(geospatialService).updateGeoPoint(id, dto);
    }

    @Test
    void deleteGeoPoint_CallsServiceAndReturnsOk() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> resp = controller.deleteGeoPoint(id);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNull(resp.getBody());
        verify(geospatialService).deleteGeoPoint(id);
    }

    @Test
    void getAllGeoPoints_NoFilter_ReturnsList() {
        List<MarkerDTO> list = List.of(new MarkerDTO(), new MarkerDTO());
        when(geospatialService.getAllGeoPoints(null)).thenReturn(list);

        ResponseEntity<List<MarkerDTO>> resp = controller.getAllGeoPoints(null);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertSame(list, resp.getBody());
        verify(geospatialService).getAllGeoPoints(null);
    }

    @Test
    void getAllGeoPoints_Paginated_ReturnsListMarkerResponse() {
        int page = 1, size = 5;
        String workStage = "CREATED";
        String landType = "Forest";
        Density density = Density.HIGH;
        String elimMethod = "Burn";
        UUID operatorId = UUID.randomUUID();
        OffsetDateTime from = OffsetDateTime.now().minusDays(1);
        OffsetDateTime to = OffsetDateTime.now();

        ListMarkerResponse respDto = new ListMarkerResponse();
        respDto.setGeoPoints(List.of(new MarkerDTO()));
        respDto.setCurrentPage(page);
        respDto.setTotalItems(10);
        respDto.setTotalPages(2);

        when(geospatialService.getAllGeoPoints(
                page, size, workStage, landType, density, elimMethod, operatorId, from, to))
                .thenReturn(respDto);

        ResponseEntity<ListMarkerResponse> resp = controller.getAllGeoPoints(
                page, size, workStage, landType, density, elimMethod, operatorId, from, to);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertSame(respDto, resp.getBody());
        verify(geospatialService).getAllGeoPoints(
                page, size, workStage, landType, density, elimMethod, operatorId, from, to);
    }

    @Test
    void addRelatedTask_CallsServiceAndReturnsOk() {
        UUID geoPointId = UUID.randomUUID();
        RelatedTaskDTO req = new RelatedTaskDTO();
        UUID taskId = UUID.randomUUID();
        req.setRelatedTaskId(taskId);

        ResponseEntity<Void> resp = controller.addRelatedTask(geoPointId, req);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNull(resp.getBody());
        verify(geospatialService).addRelatedTask(geoPointId, req);
    }

    @Test
    void getStatistic_ReturnsOkAndBody() {
        UUID operatorId = UUID.randomUUID();
        OperatorStatisticDTO stats = OperatorStatisticDTO.builder()
                .createdGeoPoints(3L)
                .processedGeoPoints(2L)
                .closedGeoPoints(1L)
                .build();

        when(geospatialService.getStatistic(operatorId)).thenReturn(stats);

        ResponseEntity<OperatorStatisticDTO> resp = controller.getStatistic(operatorId);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertSame(stats, resp.getBody());
        verify(geospatialService).getStatistic(operatorId);
    }
}






