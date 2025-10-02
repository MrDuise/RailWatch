package com.railway.hazard_reporting_system.exceptions;


public class RailSegmentNotFoundException extends RuntimeException {
    public RailSegmentNotFoundException(String message) { super(message); }
    public RailSegmentNotFoundException(String message, Throwable cause) { super(message, cause); }
}
