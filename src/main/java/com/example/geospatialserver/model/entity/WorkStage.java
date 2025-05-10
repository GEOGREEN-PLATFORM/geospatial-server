package com.example.geospatialserver.model.entity;

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