package com.FourAM.LogPulse.Utils;

import com.FourAM.LogPulse.Model.LogModel;
import org.json.JSONObject;

import java.time.Instant;

public class LogParser {
    public static LogModel parse(String rawLog) {
        LogModel logModel = new LogModel();
        logModel.setRaw(rawLog);
        logModel.setTimestamp(Instant.now());

        try {
            JSONObject json = new JSONObject(rawLog);
            logModel.setLevel(json.optString("level", "INFO"));
            logModel.setSource(json.optString("source", "UnknownService"));
            logModel.setMessage(json.optString("message", rawLog));
        } catch (Exception e) {
            if (rawLog.contains("ERROR")) logModel.setLevel("ERROR");
            else if (rawLog.contains("WARN")) logModel.setLevel("WARN");
            else logModel.setLevel("INFO");

            logModel.setSource("Unknown");
            logModel.setMessage(rawLog);
        }
        return logModel;
    }
}
