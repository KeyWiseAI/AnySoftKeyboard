package com.menny.android.anysoftkeyboard.BiAffect.Database.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class DeviceData {
    @PrimaryKey(autoGenerate = true)
    public int Id;

    @NonNull
    public String androidVersion;

    @NonNull
    public float pixelDensityLogical;

    @NonNull
    public int pixelDensityDpi;

    @NonNull
    public int deviceWidthPixel;

    @NonNull
    public int deviceHeightPixel;

    @NonNull
    public String manufacturer;

    @NonNull
    public String phoneModel;

    public DeviceData(){

    }
}
