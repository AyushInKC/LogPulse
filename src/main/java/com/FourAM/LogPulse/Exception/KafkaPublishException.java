package com.FourAM.LogPulse.Exception;

public class KafkaPublishException extends RuntimeException{
    public KafkaPublishException(String message, Throwable cause) {
        super(message, cause);
    }
}
