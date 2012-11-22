package com.osmart.event;

/**
 * @author Vadim Bobrov
 */
public class MeasurementEvent {
    private long timestamp;
    private double measurement;

    public MeasurementEvent(long timestamp, double measurement) {
        this.timestamp = timestamp;
        this.measurement = measurement;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getMeasurement() {
        return measurement;
    }
}