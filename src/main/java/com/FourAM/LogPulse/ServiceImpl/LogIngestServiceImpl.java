package com.FourAM.LogPulse.ServiceImpl;

import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Repository.LogRepository;
import com.FourAM.LogPulse.Service.LogIngestService;
import com.FourAM.LogPulse.Utils.LogParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LogIngestServiceImpl implements LogIngestService {

    private final KafkaTemplate<String, LogModel> kafkaTemplate;
    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "my-topic";

    @Override
    public void ingestLog(LogModel logModel) {
        try {
            CompletableFuture<SendResult<String, LogModel>> future = kafkaTemplate.send(TOPIC, logModel);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    System.err.println("Failed to send log to Kafka: " + ex.getMessage());
                } else {
                    System.out.println("Log sent to Kafka successfully: " + logModel);
                }
            });

            logRepository.save(logModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to publish log to Kafka", e);
        }
    }

    @Override
    public void ingestRawLog(String rawLog) {
        LogModel normalizedLog = LogParser.parse(rawLog);
        kafkaTemplate.send("my-topic", normalizedLog);
        logRepository.save(normalizedLog);
    }

    @Override
    public void ingestAllLog(String payload) {
        LogModel logModel;

        try {

            logModel = objectMapper.readValue(payload, LogModel.class);
        } catch (Exception e) {

            logModel = LogParser.parse(payload);
        }

        sendToKafkaAndSave(logModel);
    }

    @Override
    public LogModel getLogById(String id) {
        return logRepository.findById(id).orElse(null);
    }

    private void sendToKafkaAndSave(LogModel logModel) {
        try {
            kafkaTemplate.send(TOPIC, logModel);
            logRepository.save(logModel);
            System.out.println("Log ingested: " + logModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to ingest log", e);
        }
    }
}
