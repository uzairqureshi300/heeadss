package com.app.heads.Splash;

import com.android.volley.VolleyError;

/**
 * Created by warisali on 18/01/2018.
 */

public interface IResult {
    public void notifySuccess(String response);
    public void notifyError(VolleyError error);
}
