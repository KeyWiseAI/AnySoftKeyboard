package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.KeyData;

@Dao
public interface KeyData_DAO {

    @Insert
    void insertOnlySingleKeyMetrics (KeyData single_entry);
    @Insert
    void insertMultipleKeyMetrics (KeyData[] keyDataList);

    //for radius one way of adding to DB will be update at end of session. Creating the stub as placeholder for now
    //not required but kept return type int so that Roomreturns number of rows edited. This will help in debugging
    @Update
    public int updateRadiusofTouch(KeyData data);

}
