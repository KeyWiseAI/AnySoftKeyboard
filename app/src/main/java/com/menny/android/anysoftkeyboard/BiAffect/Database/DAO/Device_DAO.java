package com.menny.android.anysoftkeyboard.BiAffect.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.DeviceData;

@Dao
public interface Device_DAO {
    @Insert
    void insertDeviceData(DeviceData data);
    @Query("SELECT id FROM DeviceData")
    int[] getAllIds();
}
