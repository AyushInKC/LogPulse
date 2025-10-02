package com.FourAM.LogPulse.ServiceImpl;

import com.FourAM.LogPulse.Exception.KafkaPublishException;
import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Service.LogIngestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogIngestServiceImpl implements LogIngestService {
    private final KafkaTemplate<String, LogModel> kafkaTemplate;

    public LogIngestServiceImpl(KafkaTemplate<String, LogModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void ingestLog(LogModel logModel) {
        try {
            kafkaTemplate.send("test-topic", logModel);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish log to Kafka", e);
        }
    }
}
