package com.menny.android.anysoftkeyboard.BiAffect.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import com.menny.android.anysoftkeyboard.BiAffect.Database.DAO.Device_DAO;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.AccelerometerData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.DeviceData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.KeyTypeData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.SessionData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.TouchTypeData;

public class BiADatabaseManager {

    private static BiADatabaseManager sharedInstance;
    private static BiAffect_Database mDatabaseInstance;

    public static synchronized BiADatabaseManager getInstance(Context appContext) {
        if(sharedInstance==null){
            sharedInstance = new BiADatabaseManager(appContext);
        }
        return sharedInstance;
    }

    private BiADatabaseManager(Context appContext) {
        mDatabaseInstance = Room.databaseBuilder(appContext, BiAffect_Database.class,
                "BiAffect_Database.db")
                .fallbackToDestructiveMigration()
                .build();

    }

    //exposed API for Session
    public void insertSessionData(long sessionStartTime){
        SessionData currentSessionData = new SessionData();
        currentSessionData.sessionStartTime = sessionStartTime;
        mDatabaseInstance.mSession_dao().insertSessionStartTime(currentSessionData);
    }

    public void updateSessionData(long sessionStartTime, long sessionEndTime){
        SessionData currentSessionData = new SessionData();
        currentSessionData.sessionStartTime = sessionStartTime;
        currentSessionData.sessionEndTime = sessionEndTime;
        mDatabaseInstance.mSession_dao().updateSessionEndTime(currentSessionData);
    }

    //exposed Api for touch data
    public void insertTouchData(long eventDownTime, long eventTime, int eventAction, float pressure, float x_cord, float y_cord, float majorAxis, float minorAxis){
        TouchTypeData data = new TouchTypeData();
        data.eventDownTime = eventDownTime;
        data.eventActionTime = eventTime;
        data.eventAction = eventAction;
        data.force = pressure;
        data.touch_xcord = x_cord;
        data.touch_ycord = y_cord;
        data.touch_majorAxis = majorAxis;
        data.touch_minorAxis = minorAxis;
        mDatabaseInstance.mTouch_dao().insertSingleTouchDataEntry(data);
    }

    //exposed api for keydata
    public void insertKeyData(long keyDownTime, int keyCode, float centre_X, float centre_Y, float width, float height){
        KeyTypeData data = new KeyTypeData();
        data.keyDownTime_id = keyDownTime;
        data.keyTypeCode = keyCode;
        data.keyCentre_X = centre_X;
        data.keyCentre_Y = centre_Y;
        data.key_Width = width;
        data.key_Height = height;

        mDatabaseInstance.mKey_dao().insertSingleKeyData(data);
    }

    //accelerometer exposed apis
    public void insertAccelerometerData(long time, float x, float y, float z){
        AccelerometerData data = new AccelerometerData();
        data.time = time;
        data.Acc_X = x;
        data.Acc_Y = y;
        data.Acc_Z = z;
        mDatabaseInstance.mAccelerometer_dao().insertAccelerometerData(data);
    }

    //api exposed for Device Data
    public void insertDeviceData(Context c){
        int size=-1;
        try {
            size = mDatabaseInstance.mDevice_dao().getAllIds().length;
            Log.i("DEVICEDATA", " "+size);
        }catch (Exception e){
            Log.i("DEVICEDATA", "EXCEPTION"+e.getLocalizedMessage());
        }

        if(size==0){
            //insert the data into db
            String release = Build.VERSION.RELEASE;
            int sdkVersion = Build.VERSION.SDK_INT;
            String FINALVERSION = "Android SDK: " + sdkVersion + " (" + release +")";

            DisplayMetrics metrics = c.getResources().getDisplayMetrics();
            int densityDPI = metrics.densityDpi;
            float densityLogical = metrics.density;
            int widthPixels = metrics.widthPixels;
            int heightPixels = metrics.heightPixels;
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;

            DeviceData data = new DeviceData();
            data.androidVersion = FINALVERSION;
            data.pixelDensityDpi = densityDPI;
            data.pixelDensityLogical = densityLogical;
            data.deviceWidthPixel = widthPixels;
            data.deviceHeightPixel = heightPixels;
            data.manufacturer = manufacturer;
            data.phoneModel = model;

            mDatabaseInstance.mDevice_dao().insertDeviceData(data);



        }
    }
}
