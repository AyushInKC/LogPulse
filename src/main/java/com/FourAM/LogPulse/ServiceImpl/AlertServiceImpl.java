package com.FourAM.LogPulse.ServiceImpl;

import com.FourAM.LogPulse.Repository.LogRepository;
import com.FourAM.LogPulse.Service.AlertService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AlertServiceImpl implements AlertService {
      private final LogRepository logRepository;

      AlertServiceImpl(LogRepository logRepository){
          this.logRepository=logRepository;
      }
    @Override
    public void checkErrorThreshold() {
        Instant oneMinuteAgo = Instant.now().minus(1, ChronoUnit.MINUTES);
        long errorCount = logRepository.countByLevelAndTimestampAfter("ERROR", oneMinuteAgo);

        if (errorCount > 10) {
            System.out.println("🚨 ALERT: High error rate detected (" + errorCount + " errors in last 1 minute)");
        } else {
            System.out.println("✅ System stable. Errors in last 1 min: " + errorCount);
        }
    }
}
