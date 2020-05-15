package com.bignerdranch.android.beerkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.bignerdranch.android.beerkeeper.dao.BeehiveDao;
import com.bignerdranch.android.beerkeeper.dao.HumidityDao;
import com.bignerdranch.android.beerkeeper.dao.OxygenDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.choose_beehive)
    Spinner mSpinnerChoosePool;
    ArrayList<String> beehives = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beehive);
        ButterKnife.bind(this);
        beehives.add("Beehives");

        getAverageTemperature();
        getAverageHumidity();
        getAmount();
        getSpawning();
        getOxygen();
        getBeehiveCoordinates();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, beehives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChoosePool.setAdapter(adapter);
    }

    public void getOxygen() {
        OxygenDao t = new OxygenDao(this, "12.12.12");

        t.getLastOxygen(new OxygenDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals(Constants.ERROR) && !answer.equals(Constants.NAN)) {
                    mTextViewOxygen.setText(answer);
                } else if (answer.equals(Constants.NAN)) {
                    mTextViewOxygen.setText(Constants.NAN);
                } else {
                    mTextViewOxygen.setText(Constants.SERVICES_ERROR);
                }
            }
        });
    }

    public void getAverageTemperature() {
        TemperatureDao t = new TemperatureDao(this, "9.9.9");

        t.getLastTemperature(new TemperatureDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals(Constants.ERROR) && !answer.equals(Constants.NAN)) {
                    mTextViewDataTemperature.setText(answer);
                } else if (answer.equals(Constants.NAN)) {
                    mTextViewDataTemperature.setText(Constants.NAN);
                } else {
                    mTextViewDataTemperature.setText(Constants.SERVICES_ERROR);
                }
            }
        });
    }

    public void getAverageHumidity() {
        HumidityDao t = new HumidityDao(this, "12.12.12");

        t.getLastHumidity(new HumidityDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals(Constants.ERROR) && !answer.equals(Constants.NAN)) {
                    mTextViewHumidity.setText(answer);
                } else if (answer.equals(Constants.NAN)) {
                    mTextViewHumidity.setText(Constants.NAN);
                } else {
                    mTextViewHumidity.setText(Constants.SERVICES_ERROR);
                }
            }
        });

    }

    public void getBeehiveCoordinates() {
        BeehiveDao beehiveDao = new BeehiveDao(this, Request.Method.GET);

        beehiveDao.getCoordinates(new BeehiveDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(List<String> answer) {
                for (String coordinate : answer) {
                    beehives.add(coordinate);
                }

            }
        });

    }

    public void getAmount() {
        mTextViewAmount.setText("342");

    }

    public void getSpawning() {
        mTextViewSwarming.setText("Warning! Possible spwaning");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BeehiveActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
