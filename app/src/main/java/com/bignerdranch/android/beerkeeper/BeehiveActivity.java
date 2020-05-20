package com.bignerdranch.android.beerkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    Spinner mSpinnerChooseBeehive;
    @BindView(R.id.amount_of_frames)
    EditText mEditTextAmountOfFrames;
    ArrayList<String> beehives = new ArrayList<>();
    private String coordinates = null;

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
            public void onResult(List<String> answer) {
            }

            @Override
            public void onResult(String result) {

            }

            @Override
            public void onResult(Beehive result) {
                mEditTextAmountOfFrames.setText(result.getAmountOfFrames().toString());
                mTextViewAmount.setText(result.getAmount().toString());
                mTextViewDataTemperature.setText(result.getTemperature().toString());
                mTextViewHumidity.setText(result.getHumidity().toString());
                mTextViewOxygen.setText(result.getOxygen().toString());
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BeehiveActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
