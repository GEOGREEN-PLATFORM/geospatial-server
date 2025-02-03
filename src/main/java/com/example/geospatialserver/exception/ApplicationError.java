package com.example.geospatialserver.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationError {
    private String title;
    private String message;
    private List<String> messages;

    public ApplicationError(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public ApplicationError(String title, List<String> messages) {
        this.title = title;
        this.messages = messages;
    }
}
