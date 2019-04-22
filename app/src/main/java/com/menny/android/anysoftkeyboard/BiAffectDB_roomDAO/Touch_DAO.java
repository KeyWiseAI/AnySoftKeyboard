package com.menny.android.anysoftkeyboard.BiAffectDB_roomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.menny.android.anysoftkeyboard.BiAffectDB_roomModel.TouchData;


@Dao
public interface Touch_DAO {

    @Insert
    void insertOnlySingleTouchMEtrics (TouchData single_entry);
    @Insert
    void insertMultipleTouchMetrics (TouchData[] touchDataList);
}
