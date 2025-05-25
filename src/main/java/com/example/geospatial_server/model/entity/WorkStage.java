package com.example.geospatial_server.model.entity;

import lombok.Getter;

@Getter
public enum WorkStage {
    CREATED("Создано"),
    PROCESSED("В работе"),
    CLOSED("Завершено");

    private final String status;

    WorkStage(String status) {
        this.status = status;
    }
}