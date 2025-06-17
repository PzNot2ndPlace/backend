package ru.tbank.backend.dto;

public class WeatherResponse {
    private double temperature;
    private String condition;

    public WeatherResponse(double temperature, String condition) {
        this.temperature = temperature;
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }
}