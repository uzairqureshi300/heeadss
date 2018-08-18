package com.app.heads.PractitionerRegistration;

import com.app.heads.Models.Data;
import com.app.heads.Models.RegionsModel;

import java.util.ArrayList;

/**
 * Created by Ali Zunair on 11/08/2018.
 */

public class PractitionerRegistratopnSingleton {
    public static PractitionerRegistratopnSingleton instance = new PractitionerRegistratopnSingleton();
    private ArrayList<RegionsModel>  regions;


    public synchronized static PractitionerRegistratopnSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new PractitionerRegistratopnSingleton();
        }
        return instance;
    }

    public ArrayList<RegionsModel> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<RegionsModel> regions) {
        this.regions = regions;
    }
}
