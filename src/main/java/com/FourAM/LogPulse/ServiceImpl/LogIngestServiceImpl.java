package com.FourAM.LogPulse.ServiceImpl;

import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Repository.LogRepository;
import com.FourAM.LogPulse.Service.LogIngestService;
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

    private static final String TOPIC = "my-topic"; // replace with your topic

    @Override
    public void ingestLog(LogModel logModel) {
        try {
            // Send to Kafka asynchronously using CompletableFuture
            CompletableFuture<SendResult<String, LogModel>> future = kafkaTemplate.send(TOPIC, logModel);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    System.err.println("Failed to send log to Kafka: " + ex.getMessage());
                } else {
                    System.out.println("Log sent to Kafka successfully: " + logModel);
                }
            });

            // Save to MongoDB
            logRepository.save(logModel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to publish log to Kafka", e);
        }
    }
}
