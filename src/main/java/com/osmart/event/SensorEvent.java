package com.osmart.event;

/**
 * @author Vadim Bobrov
 */
public class SensorEvent {
    private String customer;
    private String location;
    private String circuit;

    private long timestamp;
    private double measurement;

    public SensorEvent(){}

    public SensorEvent(String customer, String location, String circuit, long timestamp, double measurement) {
        this.customer = customer;
        this.location = location;
        this.circuit = circuit;
        this.timestamp = timestamp;
        this.measurement = measurement;
    }

    public String getCustomer() {
        return customer;
    }

    public String getLocation() {
        return location;
    }

    public String getCircuit() {
        return circuit;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }
}