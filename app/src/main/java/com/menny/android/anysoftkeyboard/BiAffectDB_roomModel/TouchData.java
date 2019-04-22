package com.menny.android.anysoftkeyboard.BiAffectDB_roomModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = KeyData.class,
        parentColumns = "Key_Id",
        childColumns = "Key_Id",
        onDelete = ForeignKey.NO_ACTION),
        indices = {@Index("KeyID"),
        @Index(value = {"Key_Id"})})
public class TouchData {

    //Every field that's stored in the database needs to be either public or have a "getter" method. should not make primary key public but do not want to provide getter and setters for id.
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    public int Id;

    @NonNull
    @ColumnInfo(name = "Key_Id")
    public long eventDownTime;

    @NonNull
    @ColumnInfo(name = "Key_UP_time")
    public long eventTime;

    @NonNull
    @ColumnInfo(name = "Key_Event_Action")
    public int eventAction;

    @NonNull
    @ColumnInfo(name = "Force_of_Touch")
    public float pressure;

    @NonNull
    @ColumnInfo(name = "Key_press_X_cord")
    public float x_cord;

    @NonNull
    @ColumnInfo(name = "Key_press_Y_cord")
    public float y_cord;

    @NonNull
    @ColumnInfo(name = "Key_toucharea_Major_axis")
    public float major_axis;

    @NonNull
    @ColumnInfo(name = "Key_toucharea_Minor_axis")
    public float minor_axis;

    @NonNull
    @ColumnInfo(name = "accelerometer_x")
    public float accelerometer_x;

    @NonNull
    @ColumnInfo(name = "accelerometer_y")
    public float accelerometer_y;

    @NonNull
    @ColumnInfo(name = "accelerometer_z")
    public float accelerometer_z;

    @NonNull
    @ColumnInfo(name = "Number_of_touches")
    public int touches;

    // no setter method or initialising in constructor as primary key is autogenarated
    public TouchData() {
    }

     // public int getId() { return Id; }


}


