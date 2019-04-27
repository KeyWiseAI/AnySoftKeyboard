package com.menny.android.anysoftkeyboard.BiAffect.Database.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.menny.android.anysoftkeyboard.BiAffect.TouchDataPOJO;

import io.reactivex.annotations.NonNull;

@Entity
public class TouchTypeData {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @NonNull
    public long eventDownTime;

    @NonNull
    public long eventActionTime;

    @NonNull
    public int eventAction;

    @NonNull
    public float force;

    @NonNull
    public float touch_xcord;

    @NonNull
    public float touch_ycord;

    @NonNull
    public float touch_majorAxis;

    @NonNull
    public float touch_minorAxis;

    public TouchTypeData(){
        //Default constructor
    }

}
