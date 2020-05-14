package com.bignerdranch.android.beerkeeper.modules;

import java.util.Date;

/**
 * Created by User on 019 19.05.19.
 */

public class Beehive {

    private Long id;

    private Temperature temperature;

    private Oxygen oxygen;


    private Amount amount;

    private Humidity humidity;

    public Beehive(Long id, Temperature temperature, Oxygen oxygen, Amount amount, Humidity humidity) {
        this.id = id;
        this.temperature = temperature;
        this.oxygen = oxygen;
        this.amount = amount;
        this.humidity = humidity;
    }

    public Beehive() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Oxygen getOxygen() {
        return oxygen;
    }

    public void setOxygen(Oxygen oxygen) {
        this.oxygen = oxygen;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }
}
