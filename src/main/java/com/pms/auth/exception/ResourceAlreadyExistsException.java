package com.pms.auth.exception;

public class ResourceAlreadyExistsException extends Exception {

    private final String traceId;

    public ResourceAlreadyExistsException(String traceId, String message) {
        super(message);
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }
}
