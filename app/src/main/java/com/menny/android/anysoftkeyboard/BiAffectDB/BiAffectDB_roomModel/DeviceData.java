package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "DeviceData")
public class DeviceData {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Pixel_Density")
    public float density;

    @NonNull
    @ColumnInfo(name = "Screen_Size")
    public float size;

    @NonNull
    @ColumnInfo(name = "Phone_model")
    public String modelname;





}

