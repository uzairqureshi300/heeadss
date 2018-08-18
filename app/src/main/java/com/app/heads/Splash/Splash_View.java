package com.app.heads.Splash;

import com.android.volley.VolleyError;

/**
 * Created by warisali on 19/01/2018.
 */

public interface Splash_View {
    void showProgress();
    void hideProgress();
    void showError(VolleyError error);
}
