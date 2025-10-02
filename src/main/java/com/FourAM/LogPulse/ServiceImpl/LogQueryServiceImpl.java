package com.FourAM.LogPulse.ServiceImpl;

import com.FourAM.LogPulse.Model.LogModel;
import com.FourAM.LogPulse.Repository.LogRepository;
import com.FourAM.LogPulse.Service.LogQueryService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
public class LogQueryServiceImpl implements LogQueryService {

    private final LogRepository logRepository;

    LogQueryServiceImpl(LogRepository logRepository){
        this.logRepository=logRepository;
    }

    @Override
    public List<LogModel> searchLogs(String level, Instant start, Instant end) {
        return logRepository.findByTimestampBetween(start, end)
                .stream()
                .filter(log -> log.getLevel().equalsIgnoreCase(level))
                .toList();
    }


}
