package com.FourAM.LogPulse.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="logs")
public class LogModel {
    @Id
    private String id;
    private String source;
    private String level;
    private String message;
    private Instant timestamp;
    private List<String> tags;
    private String raw;
}
