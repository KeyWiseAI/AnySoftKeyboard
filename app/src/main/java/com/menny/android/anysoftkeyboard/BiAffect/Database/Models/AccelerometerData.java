package com.menny.android.anysoftkeyboard.BiAffect.Database.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class AccelerometerData {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @NonNull
    public long time;

    @NonNull
    public float Acc_X;

    @NonNull
    public float Acc_Y;

    @NonNull
    public float Acc_Z;

    public AccelerometerData(){

    }
}
