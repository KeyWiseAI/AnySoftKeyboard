package com.menny.android.anysoftkeyboard.BiAffect.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.AccelerometerData;

@Dao
public interface Accelerometer_DAO {
    @Insert
    void insertAccelerometerData(AccelerometerData data);
}
