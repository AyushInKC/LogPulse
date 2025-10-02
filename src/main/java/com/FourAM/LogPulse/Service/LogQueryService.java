package com.FourAM.LogPulse.Service;

import com.FourAM.LogPulse.Model.LogModel;

import java.time.Instant;
import java.util.List;

public interface LogQueryService {
   List<LogModel> searchLogs(String level, Instant start,Instant end);
}
