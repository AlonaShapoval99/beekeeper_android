package com.bignerdranch.android.beerkeeper.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bignerdranch.android.beerkeeper.Constants;
import com.bignerdranch.android.beerkeeper.modules.Amount;
import com.bignerdranch.android.beerkeeper.modules.Beehive;
import com.bignerdranch.android.beerkeeper.modules.Humidity;
import com.bignerdranch.android.beerkeeper.modules.Oxygen;
import com.bignerdranch.android.beerkeeper.modules.Temperature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 019 19.05.19.
 */

public class BeehiveDao {
    private Context mContext;
    private int method;

    public BeehiveDao(Context mContext, int method) {

        this.mContext = mContext;
        this.method = method;
    }

    public void getAllBeehive(@NonNull final TemperatureDao.BeekeeperServiceCallback callback) {
        String url = Constants.URL+"beehive/temperature/last";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Beehive beehive = new Beehive();
                        beehive.setAmount(new Amount(jsonObject.getLong("id"),
                                jsonObject.getString("measureDateAmount"),
                                jsonObject.getInt("amountOfBees")));
                        beehive.setHumidity(new Humidity(jsonObject.getLong("id"),
                                jsonObject.getString("measureDateAmount"),
                                jsonObject.getInt("humidityValue")));
                        beehive.setId(jsonObject.getLong("id"));
                        beehive.setOxygen(new Oxygen(jsonObject.getLong("id"),
                                jsonObject.getString("measureDateOxygen"),
                                jsonObject.getInt("oxygenValue")));
                        beehive.setTemperature(new Temperature(jsonObject.getLong("id"),
                                jsonObject.getString("measureDateTemperature"),
                                jsonObject.getInt("temperatureValue")));

//                        movieList.add(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        progressDialog.dismiss();
                    }
                }
//                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
//                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonArrayRequest);
    }
    }



