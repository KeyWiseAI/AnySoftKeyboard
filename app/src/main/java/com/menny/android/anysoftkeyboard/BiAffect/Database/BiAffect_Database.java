package com.menny.android.anysoftkeyboard.BiAffect.Database;

import android.arch.persistence.room.*;

import com.menny.android.anysoftkeyboard.BiAffect.Database.DAO.Accelerometer_DAO;
import com.menny.android.anysoftkeyboard.BiAffect.Database.DAO.Device_DAO;
import com.menny.android.anysoftkeyboard.BiAffect.Database.DAO.Key_DAO;
import com.menny.android.anysoftkeyboard.BiAffect.Database.DAO.Session_DAO;
import com.menny.android.anysoftkeyboard.BiAffect.Database.DAO.Touch_DAO;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.AccelerometerData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.DeviceData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.KeyTypeData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.SessionData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.TouchTypeData;

@Database(entities = {SessionData.class, TouchTypeData.class, KeyTypeData.class,
        AccelerometerData.class, DeviceData.class}, version = 1, exportSchema = false)
public abstract class BiAffect_Database extends RoomDatabase {
    public abstract Session_DAO mSession_dao();
    public abstract Touch_DAO mTouch_dao();
    public abstract Key_DAO mKey_dao();
    public abstract Accelerometer_DAO mAccelerometer_dao();
    public abstract Device_DAO mDevice_dao();
}
