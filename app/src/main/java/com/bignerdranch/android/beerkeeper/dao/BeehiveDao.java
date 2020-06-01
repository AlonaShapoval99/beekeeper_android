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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 019 19.05.19.
 */

public class BeehiveDao {
    private Context mContext;
    private String coordinates;
    private LocalDate startDate;
    private LocalDate endDate;


    public BeehiveDao(Context mContext, String coordinates) {

        this.mContext = mContext;
        this.coordinates = coordinates;
    }
    public BeehiveDao(Context mContext) {

        this.mContext = mContext;
        this.startDate = LocalDate.now().minusDays(7);
        this.endDate = LocalDate.now();
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
    public void getBeehiveByCoordinates(@NonNull final BeekeeperServiceCallback callback) {
        String url = Constants.URL + "beehive/findBeehiveByCoordinates?coordinates=" + coordinates;


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Beehive beehive = new Beehive();
                    beehive.setAmount(response.getJSONObject("amountValue").getDouble("value"));
                    beehive.setTemperature(response.getJSONObject("temperatureValue").getDouble("value"));
                    beehive.setHumidity(response.getJSONObject("humidityValue").getDouble("value"));
                    beehive.setOxygen(response.getJSONObject("oxygenValue").getDouble("value"));
                    beehive.setCoordinates(response.getString("coordinates"));
                    beehive.setAmountOfFrames(response.getInt("amountOfFrames"));
                    callback.onResult(beehive);
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
    public void getSwarming(@NonNull final BeekeeperServiceCallback callback) {
        String url = Constants.URL + "beehive/swarmingPossible?coordinates="+coordinates;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String resultMessage = response.getString("errorMessage");
                    callback.onResult(resultMessage);
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
    public void getWeatherPrediction(@NonNull final BeekeeperServiceCallback callback) {
        String url = Constants.URL + "beehive/weatherPrediction?startDate="+startDate+
                "&endDate="+endDate;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String resultMessage = response.getString("errorMessage");
                    callback.onResult(resultMessage);
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
        void onResult(List<String> result);
        void onResult(String result);
        void onResult(Beehive result);
    }
}



