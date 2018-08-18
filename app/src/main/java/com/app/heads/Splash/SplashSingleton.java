package com.app.heads.Splash;

import com.app.heads.Models.Data;
import com.app.heads.Models.Roles;

import java.util.ArrayList;

/**
 * Created by Ali Zunair on 11/08/2018.
 */

public class SplashSingleton {
    public static  SplashSingleton instance = new SplashSingleton();
    private ArrayList<Data>  roles;


    public synchronized static SplashSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new SplashSingleton();
        }
        return instance;
    }

    public ArrayList<Data> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Data> roles) {
        this.roles = roles;
    }
}
