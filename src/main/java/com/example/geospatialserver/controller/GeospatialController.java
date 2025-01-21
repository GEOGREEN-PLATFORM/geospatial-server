package com.example.geospatialserver.controller;

import com.example.geospatialserver.exception.ApplicationError;
import com.example.geospatialserver.model.dto.MarkerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "/geo/info", description = "Работа с точками на карте")
@RequestMapping("/geo/info")
public interface GeospatialController {
    @Operation(
            summary = "Получение информации о точке по id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MarkerDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Данные не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    )
            }
    )
    @GetMapping(path = "/{geoPointId}")
    ResponseEntity<MarkerDTO> getGeoPoint(@PathVariable("geoPointId") UUID geoPointId);

    @Operation(
            summary = "Сохранение информации о точке"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MarkerDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Данные не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    )
            }
    )
    @PostMapping
    ResponseEntity<MarkerDTO> createGeoPoint(@Valid @RequestBody MarkerDTO markerDTO);

    @Operation(
            summary = "Обновление информации о точке. Апдейтить можно всё кроме - uuid, square"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MarkerDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Данные не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    )
            }
    )
    @PatchMapping(path = "/{geoPointId}")
    ResponseEntity<MarkerDTO> updateGeoPoint(@PathVariable("geoPointId") UUID geoPontId, @RequestBody MarkerDTO markerDTO);

    @Operation(
            summary = "Удаление точки по id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Данные не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    )
            }
    )
    @DeleteMapping(path = "/{geoPointId}")
    ResponseEntity<Void> deleteGeoPoint(@PathVariable("geoPointId") UUID geoPointId);

    @Operation(
            summary = "Получение информации обо всех точках с типом/без типа"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = MarkerDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationError.class)
                            )
                    )
            }
    )
    @GetMapping(path = {"/getAll", "/getAll/{problemAreaType}"})
    ResponseEntity<List<MarkerDTO>> getAllGeoPoints(@PathVariable(required = false, name = "problemAreaType") String problemAreaType);
}
