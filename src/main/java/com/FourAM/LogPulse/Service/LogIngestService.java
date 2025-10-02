package com.FourAM.LogPulse.Service;

import com.FourAM.LogPulse.Model.LogModel;
import org.springframework.http.ResponseEntity;

public interface LogIngestService {
   void ingestLog(LogModel logModel);
}
