package com.example.geospatial_server.service.impl;

import com.example.geospatial_server.model.kafka.UpdateElementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, UpdateElementDTO> messageKafkaTemplate;

    @Value("${spring.kafka.topic.update-element}")
    private String updateElementTopic;

    public void produceUpdateElementMessage(UpdateElementDTO updateElementDTO) {
        messageKafkaTemplate.send(updateElementTopic, updateElementDTO);
    }
}