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
import com.bignerdranch.android.beerkeeper.modules.Oxygen;
import com.bignerdranch.android.beerkeeper.modules.Temperature;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 019 19.05.19.
 */

public class OxygenDao {
    private Context mContext;
    private String coordinates;

    public OxygenDao(Context mContext, String coordinates) {

        this.mContext = mContext;
        this.coordinates = coordinates;
    }

    public void getLastOxygen(@NonNull final OxygenDao.BeekeeperServiceCallback callback) {
        String url = Constants.URL + "beehive/currentOxygen?coordinates=" + coordinates;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onResult(response.getDouble("value") + "");
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                callback.onResult(Constants.ERROR);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonArrayRequest);
    }


    public interface BeekeeperServiceCallback {
        void onResult(String result);
    }
}
