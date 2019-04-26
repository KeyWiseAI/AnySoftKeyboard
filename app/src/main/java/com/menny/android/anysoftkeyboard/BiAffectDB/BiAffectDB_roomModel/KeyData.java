package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "KeyTypeData")
public class KeyData
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Key_Id")
    public long eventDownTime;

    //will later need to add some mapping functionality key - type
    @NonNull
    @ColumnInfo(name = "Key_Type")
    public int keyType;

    @ColumnInfo(name = "radius%_of_key_downp_ress")
    public float radius_percent;

    @NonNull
    @ColumnInfo(name = "Key_Center_X")
    public float Key_X;

    @NonNull
    @ColumnInfo(name = "Key_Center_Y")
    public float Key_Y;

    @NonNull
    @ColumnInfo(name = "Key_Width")
    public float Key_width;

    @NonNull
    @ColumnInfo(name = "Key_Height")
    public float Key_height;

    // we need to capture time of key up as well cause they need duration of a key press. Need to discuss with Viru.
}


