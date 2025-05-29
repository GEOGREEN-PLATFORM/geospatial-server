package com.example.geospatial_server.service;

import com.example.geospatial_server.model.kafka.UpdateElementDTO;
import com.example.geospatial_server.service.impl.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    @Mock
    private KafkaTemplate<String, UpdateElementDTO> kafkaTemplate;

    @InjectMocks
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(kafkaService, "updateElementTopic", "test-topic");
    }

    @Test
    void produceUpdateElementMessage_SendsToCorrectTopic() {
        UpdateElementDTO dto = UpdateElementDTO.builder()
                .elementId(UUID.randomUUID())
                .type("POINT")
                .status("CREATED")
                .build();

        kafkaService.produceUpdateElementMessage(dto);

        verify(kafkaTemplate).send("test-topic", dto);
    }
}
