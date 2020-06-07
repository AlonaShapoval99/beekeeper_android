package com.bignerdranch.android.beerkeeper;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.bignerdranch.android.beerkeeper.dao.BeehiveDao;
import com.bignerdranch.android.beerkeeper.dao.HumidityDao;
import com.bignerdranch.android.beerkeeper.dao.OxygenDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;
import com.bignerdranch.android.beerkeeper.modules.Beehive;

import java.text.DecimalFormat;
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
    @BindView(R.id.weather_prediction)
    TextView mTextViewWeatherPrediction;
    @BindView(R.id.choose_beehive)
    Spinner mSpinnerChooseBeehive;
    @BindView(R.id.amount_of_frames)
    EditText mEditTextAmountOfFrames;
    @BindView(R.id.showPredictions)
    Button mButtonShow;
    ArrayList<String> beehives = new ArrayList<>();
    private String coordinates = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beehive);
        ButterKnife.bind(this);
        beehives.add("?.?.?");

        getBeehiveCoordinates();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, beehives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChooseBeehive.setAdapter(adapter);
        mSpinnerChooseBeehive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coordinates = (String) parent.getItemAtPosition(position);
                getBeehiveByCoordinates(coordinates);
//                getSwarming(coordinates);
//                getWeatherPrediction();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
mButtonShow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSwarming(coordinates);
                getWeatherPrediction();
    }
});
    }


    private void getBeehiveCoordinates() {
        BeehiveDao beehiveDao = new BeehiveDao(this, "");

        beehiveDao.getCoordinates(new BeehiveDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(List<String> answer) {
                for (String coordinate : answer) {
                    beehives.add(coordinate);
                }

            }

            @Override
            public void onResult(String result) {

            }

            @Override
            public void onResult(Beehive result) {

            }
        });

    }

    private void getBeehiveByCoordinates(String coordinates) {
        BeehiveDao beehiveDao = new BeehiveDao(this, coordinates);

        beehiveDao.getBeehiveByCoordinates(new BeehiveDao.BeekeeperServiceCallback() {

            @Override
            public void onResult(Beehive result) {
                String frames = result.getAmountOfFrames().toString();
                mEditTextAmountOfFrames.setText(frames);
                DecimalFormat dec = new DecimalFormat("#0.00");

                mTextViewAmount.setText(dec.format(result.getAmount()));
                mTextViewDataTemperature.setText(dec.format(result.getTemperature()));
                mTextViewHumidity.setText(dec.format(result.getHumidity()));
                mTextViewOxygen.setText(dec.format(result.getOxygen()));

            }
            @Override
            public void onResult(List<String> answer) {
            }

            @Override
            public void onResult(String result) {

            }

        });

    }

    private void getSwarming(String coordinates) {
        BeehiveDao beehiveDao = new BeehiveDao(this, coordinates);

        beehiveDao.getSwarming(new BeehiveDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(List<String> answer) {
            }

            @Override
            public void onResult(String result) {
                mTextViewSwarming.setText(result);
            }

            @Override
            public void onResult(Beehive result) {

            }
        });
        getBeehiveByCoordinates( coordinates);
    }

    private void getWeatherPrediction() {
        BeehiveDao beehiveDao = new BeehiveDao(this);

        beehiveDao.getWeatherPrediction(new BeehiveDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(List<String> answer) {
            }

            @Override
            public void onResult(String result) {
                mTextViewWeatherPrediction.setText(result);
            }

            @Override
            public void onResult(Beehive result) {

            }
        });
        getBeehiveByCoordinates( coordinates);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BeehiveActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
