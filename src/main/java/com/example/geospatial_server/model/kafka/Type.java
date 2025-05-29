package com.example.geospatial_server.model.kafka;

import lombok.Getter;

@Getter
public enum Type {
    POINT("Очаг");

    private final String name;

    Type(String s) {
        this.name = s;
    }
}