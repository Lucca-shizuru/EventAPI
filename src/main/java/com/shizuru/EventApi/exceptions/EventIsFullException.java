package com.shizuru.EventApi.exceptions;

public class EventIsFullException extends RuntimeException {
    public EventIsFullException(String message) {
        super(message);
    }
}
