package com.menny.android.anysoftkeyboard.BiAffect.Database.Models;

import android.arch.persistence.room.*;

import io.reactivex.annotations.NonNull;

@Entity
public class SessionData {
    @PrimaryKey
    @NonNull
    public long sessionStartTime;
    public long sessionEndTime;

    public SessionData(){

    }

}
