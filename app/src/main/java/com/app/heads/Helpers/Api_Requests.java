package com.app.heads.Helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.heads.Splash.IResult;
import com.app.heads.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali Zunair on 10/08/2018.
 */

public class Api_Requests implements Response.Listener<String>,Response.ErrorListener {

    IResult mResultCallback = null;
    Context mContext;


    public Api_Requests(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }
    public void getRoles(String method) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            StringRequest jsonObj = new StringRequest(Request.Method.GET, Constants.base_api+method,this,this);
            queue.add(jsonObj);
        } catch (Exception e) {
        }
    }
    public void getRegions(String method) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            StringRequest jsonObj = new StringRequest(Request.Method.GET, Constants.base_api+method,this,this);
            queue.add(jsonObj);
        } catch (Exception e) {
        }
    }
    public void RegisterUser(String method, final String first_name, final String last_name, final String email, final String password, final String region_id) {
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);
            final StringRequest jsonObj = new StringRequest(Request.Method.POST, Constants.base_api+method,this,this) {

                @Override
                protected Map<String, String> getParams() {
                    // Post params to login url
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("first_name", first_name);
                    params.put("last_name", last_name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("region_id", region_id);


                    return params;
                }

            } ;
            queue.add(jsonObj);
        } catch (Exception e) {
        }
    }
    public void LoginUser(String method, final String username, final String password) {
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);
            final StringRequest jsonObj = new StringRequest(Request.Method.POST, Constants.base_api+method,this,this) {

                @Override
                protected Map<String, String> getParams() {
                    // Post params to login url
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("username", username);
                    params.put("password", password);

                    return params;
                }

            } ;
            queue.add(jsonObj);
        } catch (Exception e) {
        }
    }
    public void ForgotPassword(String method, final String email) {
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);
            final StringRequest jsonObj = new StringRequest(Request.Method.POST, Constants.base_api+method,this,this) {

                @Override
                protected Map<String, String> getParams() {
                    // Post params to login url
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email", email);

                    return params;
                }

            } ;
            queue.add(jsonObj);
        } catch (Exception e) {
        }
    }

    public void FetchQuestions(String method, final String domain_id,final String role_id) {
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);
            final StringRequest jsonObj = new StringRequest(Request.Method.POST, Constants.base_api+method,this,this) {

                @Override
                protected Map<String, String> getParams() {
                    // Post params to login url
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("domain_id", domain_id);
                    params.put("role_id",role_id);

                    return params;
                }

            } ;
            queue.add(jsonObj);
        } catch (Exception e) {
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mResultCallback != null)
            mResultCallback.notifyError(error);

    }

    @Override
    public void onResponse(String response) {
        if (mResultCallback != null)
            mResultCallback.notifySuccess(response);

    }
}
