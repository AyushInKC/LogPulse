package com.FourAM.LogPulse.Controller;
import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Service.LogIngestService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class LogIngestController {
   private final LogIngestService logIngestService;
   LogIngestController(LogIngestService logIngestService){
       this.logIngestService=logIngestService;
   }
    @PostMapping("/ingestStructured")
    public ResponseEntity<?> ingestLog(@RequestBody LogModel logModel){
         logIngestService.ingestLog(logModel);
        return ResponseEntity.ok("Structured Logs sent to Kafka");
    }

    @PostMapping("/ingestUnStructured")
    public ResponseEntity<String> ingestRawLog(@RequestBody String rawLog) {
        logIngestService.ingestRawLog(rawLog);
        return ResponseEntity.ok("Raw log ingested successfully and sent to Kafka");
    }

    @PostMapping("/ingestAll")
    public ResponseEntity<?> ingestAll(@RequestBody Payload payload){
       logIngestService.ingestAllLog(String.valueOf(payload));
       return ResponseEntity.ok("All the logs are Successfully Ingested and sent to Kafka");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLogsById(@PathVariable String id){
        try {
            LogModel log = logIngestService.getLogById(id);
            if (log != null) {
                return ResponseEntity.ok(log);
            } else {
                return ResponseEntity.status(404).body("Log not found with id: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching log: " + e.getMessage());
        }
    }
}
