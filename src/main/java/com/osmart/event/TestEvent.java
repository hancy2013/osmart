package com.osmart.event;

/**
 * @author Vadim Bobrov
 */
public class TestEvent {
    private String customer;
    private String location;
    private String circuit;

    private double measurement;

    public TestEvent(){

    }

    public TestEvent(String customer, String location, String circuit, double measurement) {
        this.customer = customer;
        this.location = location;
        this.circuit = circuit;
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

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }
}