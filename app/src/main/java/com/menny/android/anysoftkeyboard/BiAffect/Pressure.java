package com.menny.android.anysoftkeyboard.BiAffect;

public class Pressure extends BiAFeature{
    double value;

    public Pressure(String TAG, long id, double value, int action){
        this.TAG = TAG;
        this.id = id;
        this.value = value;
        this.action = action;
    }
}

