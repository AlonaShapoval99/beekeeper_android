package com.bignerdranch.android.beerkeeper.modules;

import java.util.Date;

/**
 * Created by User on 019 19.05.19.
 */

public class Humidity {
    private Long id;
    private String date;
    private Integer value;

    public Humidity(Long id, String date, Integer value) {
        this.id = id;
        this.date = date;
        this.value = value;
    }

    public Humidity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
