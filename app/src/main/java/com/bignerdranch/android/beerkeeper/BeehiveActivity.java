package com.bignerdranch.android.beerkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.bignerdranch.android.beerkeeper.dao.HumidityDao;
import com.bignerdranch.android.beerkeeper.dao.OxygenDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeehiveActivity extends AppCompatActivity {

    @BindView(R.id.date_temperature)
    TextView mTextViewDataTemperature;
    @BindView(R.id.date_humidity)
    TextView mTextViewHumidity;
    @BindView(R.id.date_oxygen)
    TextView mTextViewOxygen;
    @BindView(R.id.date_amount)
    TextView mTextViewAmount;
    @BindView(R.id.swarming)
    TextView mTextViewSwarming;

    int averageTemperature;
    int averageWaterCondition;
    int fishAge;
    @BindView(R.id.choose_beehive)
    Spinner mSpinnerChoosePool;
    ArrayList<String> pools = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beehive);
        ButterKnife.bind(this);
        pools.add("Beehives");
        getPools();

        getAverageTemperature();
        getAverageHumidity();
        getAmount();
        getSpawning();
        getOxygen();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pools);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChoosePool.setAdapter(adapter);
    }
public void getOxygen(){
    OxygenDao t = new OxygenDao(this, Request.Method.GET);

    t.getLastOxygen(new OxygenDao.BeekeeperServiceCallback() {
        @Override
        public void onResult(String answer) {
            if (!answer.equals("Error")) {
                mTextViewOxygen.setText(answer);
            } else {
                mTextViewOxygen.setText("80 %");
            }
        }
    });
}
    public void getAverageTemperature() {
        TemperatureDao t = new TemperatureDao(this, "9.9.9");

        t.getLastTemperature(new TemperatureDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals("Error")) {
                    mTextViewDataTemperature.setText(answer);
                } else {
                    mTextViewDataTemperature.setText("24 C");
                }
            }
        });
    }

    public void getAverageHumidity() {
HumidityDao t = new HumidityDao(this, Request.Method.GET);

        t.getLastHumidity(new HumidityDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals("Error")) {
                    mTextViewHumidity.setText(answer);
                } else {
                    mTextViewHumidity.setText("80 %" );
                }
            }
        });

    }

    public void getAmount() {
        mTextViewAmount.setText("342");

    }

    public void getSpawning(){
        mTextViewSwarming.setText("Warning! Possible spwaning");

    }
    public void getPools() {

    }

    public boolean isPossibleSpawning() {
        if (fishAge >= Constants.MIN_FISH_AGE && averageTemperature >= Constants.MIN_POSSIBLE_TEMPERATURE
                && averageTemperature <= Constants.MAX_POSSIBLE_TEMPERATURE &&
                averageWaterCondition <= Constants.MAX_POSSIBLE_WATER_CONDITION
                ) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BeehiveActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
