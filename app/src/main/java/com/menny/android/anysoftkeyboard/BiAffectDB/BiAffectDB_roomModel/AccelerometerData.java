package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class AccelerometerData {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public long id;

    @NonNull
    @ColumnInfo(name = "Key_press_time")
    public long eventTime;

    @NonNull
    @ColumnInfo(name = "accelerometer_x")
    public float accelerometer_x;

    @NonNull
    @ColumnInfo(name = "accelerometer_y")
    public float accelerometer_y;

    @NonNull
    @ColumnInfo(name = "accelerometer_z")
    public float accelerometer_z;

}
