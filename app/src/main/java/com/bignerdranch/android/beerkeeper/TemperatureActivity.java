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
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;

import java.util.ArrayList;

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
       // getPools();

        mButtonTemperatureMeasuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               measureTemperature();

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, beehives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChooseBeehive.setAdapter(adapter);

    }

    public void measureTemperature() {
        TemperatureDao t = new TemperatureDao(this, "9.9.9");

        t.getLastTemperature(new TemperatureDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals("Error")) {
                    mTextViewDegreeTemperature.setText(formatValue(answer));
                } else {
                    mTextViewDegreeTemperature.setText("24 C");
                }
            }
        });
       // t.getLastTemperature();

    }
//
//    public void getPools() {
//        Pool t = new Pool(this, Request.Method.GET);
//
//        t.getPools(new Pool.BeekeeperServiceCallback() {
//            @Override
//            public void onResult(String answer) {
//                if (!answer.equals("Error")) {
//                    beehives.add("Pools #" + answer);
//                } else {
//                    // mTextViewDegreeTemperature.setText("Error");
//                }
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TemperatureActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private String formatValue(String val) {

        return "Current temperature: " + val + " C";
    }
}
