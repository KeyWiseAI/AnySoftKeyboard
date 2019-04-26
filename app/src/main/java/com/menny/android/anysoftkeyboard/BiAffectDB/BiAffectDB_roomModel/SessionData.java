package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class SessionData {

    public long id;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Session_start_time")
    public long startTime;

    @NonNull
    @ColumnInfo(name = "Session_end_time")
    public long endTime;
}