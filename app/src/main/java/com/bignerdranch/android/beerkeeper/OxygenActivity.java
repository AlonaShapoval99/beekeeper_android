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
import com.bignerdranch.android.beerkeeper.dao.OxygenDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;

import java.util.ArrayList;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxygen);
        ButterKnife.bind(this);
        beehives.add("Beehives");
        getPools();

        mButtonOxygenMeasuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measureOxygen();

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, beehives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChooseBeehive.setAdapter(adapter);

    }

    public void measureOxygen() {
        OxygenDao t = new OxygenDao(this, Request.Method.GET);

        t.getLastOxygen(new OxygenDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals("Error")) {
                    mTextViewDegreeOxygen.setText(formatValue(answer));
                } else {
                    mTextViewDegreeOxygen.setText("90 %");
                }
            }
        });

    }

    public void getPools() {
//        Pool t = new Pool(this, Request.Method.GET);
//
//        t.getPools(new Pool.BeekeeperServiceCallback() {
//            @Override
//            public void onResult(String answer) {
//                if (!answer.equals("Error")) {
//                    beehives.add("Pools #" + answer);
//                } else {
//                    // mTextViewDegreeOxygen.setText("Error");
//                }
//            }
//        });
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
