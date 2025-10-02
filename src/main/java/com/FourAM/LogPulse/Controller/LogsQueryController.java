package com.FourAM.LogPulse.Controller;

import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Repository.LogRepository;
import com.FourAM.LogPulse.Service.LogQueryService;
import org.apache.kafka.clients.consumer.internals.events.ResetPositionsEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsQueryController {

   private final LogRepository logRepository;
  private final LogQueryService logQueryService;
   LogsQueryController(LogRepository logRepository,LogQueryService logQueryService){
       this.logRepository=logRepository;
       this.logQueryService=logQueryService;
   }

   @GetMapping("/search")
    public List<LogModel> searchLogs(@RequestParam String level,
                                     @RequestParam Instant start,
                                     @RequestParam Instant end){

       return logQueryService.searchLogs(level,start,end);
   }
}
