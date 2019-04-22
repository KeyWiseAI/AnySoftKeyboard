package com.menny.android.anysoftkeyboard.BiAffectDB_roomModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class KeyData
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Key_Id")
    public long eventDownTime;

    @NonNull
    @ColumnInfo(name = "Key_Type")
    public long keyType;

}


