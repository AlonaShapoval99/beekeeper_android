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
import com.bignerdranch.android.beerkeeper.dao.HumidityDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;
import com.bignerdranch.android.beerkeeper.modules.Beehive;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HumidityActivity extends AppCompatActivity {

    @BindView(R.id.degree_humidity)
    TextView mTextViewDegreeHumidity;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.measure)
    Button mButtonHumidityMeasuring;
    @BindView(R.id.choose_beehive)
    Spinner mSpinnerChooseBeehive;
    ArrayList<String> beehives = new ArrayList<>();
    private String coordinates = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        ButterKnife.bind(this);
        beehives.add("?.?.?");

        mButtonHumidityMeasuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measureHumidity();

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

    public void getBeehiveCoordinates() {
        BeehiveDao beehiveDao = new BeehiveDao(this, "Request.Method.GET");

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

    public void measureHumidity() {
        HumidityDao t = new HumidityDao(this, coordinates);

        t.getLastHumidity(new HumidityDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals(Constants.ERROR) && !answer.equals(Constants.NAN)) {
                    mTextViewDegreeHumidity.setText(formatValue(answer));
                } else if (answer.equals(Constants.NAN)) {
                    mTextViewDegreeHumidity.setText(Constants.NO_DATA_FOR_DATE);
                } else {
                    mTextViewDegreeHumidity.setText(Constants.SERVICES_ERROR);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HumidityActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private String formatValue(String val) {

        return "Current humidity: " + val + " ";
    }
}
