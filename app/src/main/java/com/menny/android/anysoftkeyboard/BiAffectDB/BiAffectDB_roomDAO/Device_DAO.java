package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.DeviceData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.TouchData;

@Dao
public interface Device_DAO {
    @Insert
    void insertDeviceData (DeviceData single_entry);

    //one thing need to check - read that room throws error if no value returned. Workaround for this.

    //will be required to fetch records to coords check that duplicate data is not inserted. insert only if no records returned.
    // as assuming that capturing only 1 device's entries - wrapper will be provided by Sage no need of a where clause. Any entry in table means device data stored.
    @Query("SELECT id FROM DeviceData ")
    public int fetchDeviceId();

}
