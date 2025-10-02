package com.FourAM.LogPulse.Controller;
import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Service.LogIngestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogIngestController {
   private final LogIngestService logIngestService;
   LogIngestController(LogIngestService logIngestService){
       this.logIngestService=logIngestService;
   }
    @PostMapping("/ingest")
    public ResponseEntity<?> ingestLog(@RequestBody LogModel logModel){
         logIngestService.ingestLog(logModel);
        return ResponseEntity.ok("Log sent to Kafka");
    }
}
