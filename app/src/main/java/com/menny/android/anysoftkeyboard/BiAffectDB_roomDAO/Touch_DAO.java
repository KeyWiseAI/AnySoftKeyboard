package com.menny.android.anysoftkeyboard.BiAffectDB_roomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.menny.android.anysoftkeyboard.BiAffectDB_roomModel.TouchData;
/**
 * Created by Sreetama Banerjee on 4/22/2019.
 * reason : room DAO
 */

@Dao
public interface Touch_DAO {

    @Insert
    void insertOnlySingleTouchMetrics (TouchData single_entry);
    @Insert
    void insertMultipleTouchMetrics (TouchData[] touchDataList);

    // to test if data is being stored in DB
    @Query("SELECT * FROM TouchData")
    TouchData fetchTouchDataAll ();



}
