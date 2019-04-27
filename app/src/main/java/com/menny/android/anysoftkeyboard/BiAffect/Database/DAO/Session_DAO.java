package com.menny.android.anysoftkeyboard.BiAffect.Database.DAO;

import android.arch.persistence.room.*;

import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.SessionData;

@Dao
public interface Session_DAO {
    @Insert
    void insertSessionStartTime(SessionData currentSessionData);
    @Update
    int updateSessionEndTime(SessionData currentSessionData);
    @Query("SELECT * FROM SESSIONDATA")
    SessionData[] getAll();


}
