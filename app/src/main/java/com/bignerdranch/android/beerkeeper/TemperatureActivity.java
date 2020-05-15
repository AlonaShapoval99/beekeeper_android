package com.bignerdranch.android.beerkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.bignerdranch.android.beerkeeper.dao.BeehiveDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemperatureActivity extends AppCompatActivity {

    @BindView(R.id.degree_temperature)
    TextView mTextViewDegreeTemperature;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.measure)
    Button mButtonTemperatureMeasuring;
    @BindView(R.id.choose_beehive)
    Spinner mSpinnerChooseBeehive;
    ArrayList<String> beehives = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        ButterKnife.bind(this);
        beehives.add("Beehives");

        mButtonTemperatureMeasuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measureTemperature();

            }
        });
        getBeehiveCoordinates();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, beehives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChooseBeehive.setAdapter(adapter);

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

    public void measureTemperature() {
        TemperatureDao t = new TemperatureDao(this, "9.9.9");

        t.getLastTemperature(new TemperatureDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals(Constants.ERROR) && !answer.equals(Constants.NAN)) {
                    mTextViewDegreeTemperature.setText(formatValue(answer));
                } else if (answer.equals(Constants.NAN)) {
                    mTextViewDegreeTemperature.setText(Constants.NO_DATA_FOR_DATE);
                } else {
                    mTextViewDegreeTemperature.setText(Constants.SERVICES_ERROR);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TemperatureActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private String formatValue(String val) {

        return "Current temperature: " + val + " C";
    }
}
