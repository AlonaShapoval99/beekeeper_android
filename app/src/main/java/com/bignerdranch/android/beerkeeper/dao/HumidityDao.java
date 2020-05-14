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
import com.bignerdranch.android.beerkeeper.modules.Humidity;
import com.bignerdranch.android.beerkeeper.modules.Temperature;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 019 19.05.19.
 */

public class HumidityDao {
    private Context mContext;
    private int method;

    public HumidityDao(Context mContext, int method) {

        this.mContext = mContext;
        this.method = method;
    }

    public void getLastHumidity(@NonNull final HumidityDao.BeekeeperServiceCallback callback) {
        String url = Constants.URL+"beehive/humidity/last";


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                for (int i = 0; i < response.length(); i++) {
                try {
//                        JSONObject jsonObject = response.getJSONObject(i);

                    Humidity humidity = new Humidity();
                    humidity.setId(response.getLong("id"));
                    humidity.setDate(response.getString("measureDateAmount"));
                    humidity.setValue(response.getInt("humidityValue"));
                    callback.onResult(humidity.getValue()+"");
                } catch (JSONException e) {
                    e.printStackTrace();

                }
//                }

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
