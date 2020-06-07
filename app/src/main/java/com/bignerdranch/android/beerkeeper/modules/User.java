package com.bignerdranch.android.beerkeeper.modules;

public class User {
    private long id;
    private boolean wasLogined;

    public User(long id, boolean wasLogined) {
        this.id = id;
        this.wasLogined = wasLogined;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isWasLogined() {
        return wasLogined;
    }

    public void setWasLogined(boolean wasLogined) {
        this.wasLogined = wasLogined;
    }
}
