package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.SessionData;

@Dao
public interface Session_DAO {

    //while implementing we will have to add starttime as end time as end time and later update it
    @Insert
    void insertSessionStartTime (SessionData single_entry);

    @Update
    void updateSessionEndTime(SessionData data);
}
