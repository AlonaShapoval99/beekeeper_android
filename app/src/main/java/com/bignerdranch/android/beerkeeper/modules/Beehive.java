package com.bignerdranch.android.beerkeeper.modules;

import java.util.Date;

/**
 * Created by User on 019 19.05.19.
 */

public class Beehive {

    private String coordinates;

    private Double temperature;

    private Double oxygen;


    private Double amount;

    private Double humidity;

    private Integer amountOfFrames;


    public Beehive(String coordinates, Double temperature, Double oxygen, Double amount, Double humidity, Integer amountOfFrames) {
        this.coordinates = coordinates;
        this.temperature = temperature;
        this.oxygen = oxygen;
        this.amount = amount;
        this.humidity = humidity;
        this.amountOfFrames = amountOfFrames;
    }

    public Beehive() {
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getOxygen() {
        return oxygen;
    }

    public void setOxygen(Double oxygen) {
        this.oxygen = oxygen;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getAmountOfFrames() {
        return amountOfFrames;
    }

    public void setAmountOfFrames(Integer amountOfFrames) {
        this.amountOfFrames = amountOfFrames;
    }
}
