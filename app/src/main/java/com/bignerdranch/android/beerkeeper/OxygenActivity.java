package com.bignerdranch.android.beerkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.bignerdranch.android.beerkeeper.dao.BeehiveDao;
import com.bignerdranch.android.beerkeeper.dao.OxygenDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;
import com.bignerdranch.android.beerkeeper.modules.Beehive;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OxygenActivity extends AppCompatActivity {

    @BindView(R.id.degree_oxygen)
    TextView mTextViewDegreeOxygen;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.measure)
    Button mButtonOxygenMeasuring;
    @BindView(R.id.choose_beehive)
    Spinner mSpinnerChooseBeehive;
    ArrayList<String> beehives = new ArrayList<>();
    private String coordinates = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxygen);
        ButterKnife.bind(this);
        beehives.add("?.?.?");

        mButtonOxygenMeasuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measureOxygen();

            }
        });
        getBeehiveCoordinates();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, beehives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChooseBeehive.setAdapter(adapter);
        mSpinnerChooseBeehive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coordinates = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void measureOxygen() {
        OxygenDao t = new OxygenDao(this, coordinates);

        t.getLastOxygen(new OxygenDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals(Constants.ERROR) && !answer.equals(Constants.NAN)) {
                    mTextViewDegreeOxygen.setText(formatValue(answer));
                } else if (answer.equals(Constants.NAN)) {
                    mTextViewDegreeOxygen.setText(Constants.NO_DATA_FOR_DATE);
                } else {
                    mTextViewDegreeOxygen.setText(Constants.SERVICES_ERROR);
                }
            }
        });

    }

    public void getBeehiveCoordinates() {
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OxygenActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private String formatValue(String val) {

        return "Current oxygen: " + val + " ";
    }
}
