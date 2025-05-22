package org.example.diplomamainservice.Dto;

public class TtsRequest {
    private String text;
    private double temperature = 0.4;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
}
