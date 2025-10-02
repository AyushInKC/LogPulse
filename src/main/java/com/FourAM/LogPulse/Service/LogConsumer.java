package com.FourAM.LogPulse.Service;

import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;

public class LogConsumer {
    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    LogConsumer(LogRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "log-topic", groupId = "logpulse-group")
    public void consume(String logJson) {
        try {
            LogModel logModel = objectMapper.readValue(logJson, LogModel.class);
            logRepository.save(logModel);
            System.out.println("💾 Log saved to MongoDB: " + logModel.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}