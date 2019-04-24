package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.AccelerometerData;


@Dao
public interface Accelerometer_DAO {

    @Insert
    void insertOnlySingleAccelerometerEntry (AccelerometerData single_entry);
    @Insert
    void insertMultipleAccelerometerEntry (AccelerometerData[] DataList);
}
