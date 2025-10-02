package com.FourAM.LogPulse.Service;

import com.FourAM.LogPulse.Model.LogModel;

public interface LogIngestService {
   void ingestLog(LogModel logModel);
    void ingestRawLog(String rawLog);
    void ingestAllLog(String payload);

    LogModel getLogById(String id);
}
