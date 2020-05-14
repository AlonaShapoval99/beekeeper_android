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
import com.bignerdranch.android.beerkeeper.dao.HumidityDao;
import com.bignerdranch.android.beerkeeper.dao.TemperatureDao;

import java.util.ArrayList;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        ButterKnife.bind(this);
        beehives.add("Beehives");
        getPools();

        mButtonHumidityMeasuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measureHumidity();

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, beehives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChooseBeehive.setAdapter(adapter);

    }

    public void measureHumidity() {
 HumidityDao t = new HumidityDao(this, Request.Method.GET);

        t.getLastHumidity(new HumidityDao.BeekeeperServiceCallback() {
            @Override
            public void onResult(String answer) {
                if (!answer.equals("Error")) {
                    mTextViewDegreeHumidity.setText(formatValue(answer));
                } else {
                    mTextViewDegreeHumidity.setText("80 %");
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
//                    // mTextViewDegreeHumidity.setText("Error");
//                }
//            }
//        });
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
