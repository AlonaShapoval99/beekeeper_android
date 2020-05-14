package com.bignerdranch.android.beerkeeper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by User on 019 19.05.19.
 */

public class CurrentBeehive {
    private Context mContext;
//    private int method;
//
//    public Spawning(Context mContext, int method) {
//        this.mContext = mContext;
//        this.method = method;
//    }
//
//    public void getAverageWaterCondition(@NonNull final BeekeeperServiceCallback callback){
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//        String url =Constants.URL+"average_water_condition";
//
//        StringRequest stringRequest = new StringRequest(method, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        callback.onResult(response.substring(0,4));
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onResult("Error");
//            }
//        });
//
//
//        queue.add(stringRequest);
//
//    }
//    public void getAverageTemperature(@NonNull final BeekeeperServiceCallback callback){
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//        String url =Constants.URL+"average_temperature";
//
//        StringRequest stringRequest = new StringRequest(method, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        callback.onResult(response.substring(0,4));
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onResult("Error");
//            }
//        });
//
//
//        queue.add(stringRequest);
//
//    }
//    public void getAmountOfFish(@NonNull final BeekeeperServiceCallback callback){
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//        String url =Constants.URL+"pool/1?param=amount_of_fish";
//
//        StringRequest stringRequest = new StringRequest(method, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        callback.onResult(response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onResult("Error");
//            }
//        });
//
//
//        queue.add(stringRequest);
//
//    }
//    public void getAdvice(@NonNull final BeekeeperServiceCallback callback){
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//        String url =Constants.URL+"pool/1?param=advice";
//
//        StringRequest stringRequest = new StringRequest(method, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        callback.onResult(response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onResult("Error");
//            }
//        });
//
//
//        queue.add(stringRequest);
//
//    }
//    public interface BeekeeperServiceCallback {
//        void onResult(String result);
//    }
}
