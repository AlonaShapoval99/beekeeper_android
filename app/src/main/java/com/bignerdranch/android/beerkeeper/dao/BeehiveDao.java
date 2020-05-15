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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void getCoordinates(@NonNull final BeekeeperServiceCallback callback) {
        String url = Constants.URL + "beehive/beehiveCoordinates";
        JsonArrayRequest  jsonArrayRequest = new JsonArrayRequest (Request.Method.GET,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    List<String> coordinates = new ArrayList<>();
                    // Loop through the array elements
                    for(int i=0;i<response.length();i++){
                        // Get current json object

                       coordinates.add(response.getJSONObject(i).getString("coordinates"));

                    }
                    callback.onResult(coordinates);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                callback.onResult(Arrays.asList(Constants.ERROR));

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonArrayRequest);
        //return coordinates;
    }

    public interface BeekeeperServiceCallback {
        void onResult(List<String> result);
    }
}



