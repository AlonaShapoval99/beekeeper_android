package com.bignerdranch.android.beerkeeper.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bignerdranch.android.beerkeeper.Constants;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 005 05.01.19.
 */

public class Login {
    private Context mContext;
    private int method;
    private String email;
    private String password;

    public Login(Context mContext, int method, String email, String password) {
        this.mContext = mContext;
        this.method = method;
        this.email = email;
        this.password = password;
    }

    public void checkLogin(@NonNull final BeekeeperServiceCallback callback) {
        String url = Constants.URL + "worker/loginUser?email=" + email + "&password=" + password;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    callback.onResult(response.getBoolean("wasLogined"));
                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                callback.onResult(false);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonArrayRequest);
    }

    public interface BeekeeperServiceCallback {
        void onResult(boolean result);
    }

}
