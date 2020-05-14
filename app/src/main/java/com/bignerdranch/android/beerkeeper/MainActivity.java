package com.bignerdranch.android.beerkeeper;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.temperature_measuring)
    LinearLayout mLinearLayoutTemperatureMeasuring;
    @BindView(R.id.humidity)
    LinearLayout mLinearLayoutHumidity;
    @BindView(R.id.oxygen)
    LinearLayout mLinearLayoutOxygen;
    @BindView(R.id.beehive)
    LinearLayout mLinearLayoutBeehive;
    static String language = "uk";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLinearLayoutTemperatureMeasuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TemperatureActivity.class);
                startActivity(intent);
            }
        });

        mLinearLayoutHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HumidityActivity.class);
                startActivity(intent);
            }
        });

        mLinearLayoutOxygen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OxygenActivity.class);
                startActivity(intent);
            }
        });

        mLinearLayoutBeehive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BeehiveActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Операции для выбранного пункта меню
        switch (item.getItemId()) {
            case R.id.change_locale:
                changeLocale();
                return true;
            case R.id.log_out:
                LogOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeLocale() {
        Locale locale = null;
        if (language.equalsIgnoreCase("en")) {
            locale = new Locale("en");
            language = "uk";
        } else if (language.equalsIgnoreCase("uk")) {
            locale = new Locale("uk");
            language = "en";
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void LogOut() {
        Toast.makeText(MainActivity.this, "Log out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("EXTRA_SESSION_ID", "0");
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(MainActivity.this);
        //finishAffinity();

    }
}