package com.bignerdranch.android.beerkeeper.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bignerdranch.android.beerkeeper.Constants;
import com.bignerdranch.android.beerkeeper.modules.Temperature;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by User on 019 19.05.19.
 */

public class TemperatureDao {
    private Context mContext;
    private String coordinates;


    public TemperatureDao(Context mContext, String method) {

        this.mContext = mContext;
        this.coordinates = method;
    }

    public void getLastTemperature(@NonNull final BeekeeperServiceCallback callback) {
        String url =Constants.URL+"beehive/currentTemp?coordinates="+coordinates;


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    try {
                        Temperature temperature=new Temperature();
                        temperature.setId(response.getLong("id"));
                        temperature.setDate(response.getString("measureDateTemperature"));
                        temperature.setValue(response.getInt("temperatureValue"));
                        callback.onResult(temperature.getValue()+"");
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                callback.onResult("Error");

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonArrayRequest);
    }


    public interface BeekeeperServiceCallback {
        void onResult(String result);
    }
}
