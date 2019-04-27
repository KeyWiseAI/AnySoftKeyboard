package com.menny.android.anysoftkeyboard.BiAffectDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.text.method.Touch;
import android.util.Log;

import com.menny.android.anysoftkeyboard.AnyApplication;
import com.menny.android.anysoftkeyboard.BiAffect.TouchDataWorker;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomDAO.Touch_DAO;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.AccelerometerData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.DeviceData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.KeyData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.SessionData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.TouchData;

public class BiAffectDBManager implements BiAffectDBInterface.TouchDataInterface, BiAffectDBInterface.SessionDataInterface, BiAffectDBInterface.KeyTypeDataInterface,BiAffectDBInterface.DeviceDataInterface, BiAffectDBInterface.AccelerometerDataInterface{
    /**
     * Created by Sreetama Banerjee on 4/22/2019.
     * reason : to allow all components of project to get appcontext
     */
    Context mcontext = AnyApplication.getAppContext();

    private static BiAffectDB DBINSTANCE;
    private final Touch_DAO mtouchDataDao;

    private static BiAffectDBManager MngrInstance = null;

    public static synchronized BiAffectDBManager getInstance() {
            if (MngrInstance == null)
                MngrInstance = new BiAffectDBManager();
            return MngrInstance;
        }

    //can put static if need be
    private BiAffectDBManager() {
        DBINSTANCE= Room.databaseBuilder(mcontext,
                BiAffectDB.class, "BiAffect_database.db")
                .fallbackToDestructiveMigration()
                .build();
        mtouchDataDao = DBINSTANCE.TouchDataDao();
    }

    @Override
    public void insertTouchTypeData(long eventDowntime,long eventTime,int eventAction,float pressure,float x, float y,float major_Axis,float minor_axis){
        Log.i("CS_BiA_TP_T","Start");
        Log.i("CS_BiA_TP_T","downTime -> "+eventDowntime);
        TouchData TouchDataObj =new TouchData();
        TouchDataObj.eventDownTime=eventDowntime;
        TouchDataObj.eventTime = eventTime;
        TouchDataObj.eventAction=eventAction;
        TouchDataObj.pressure=pressure;
        TouchDataObj.x_cord=x;
        TouchDataObj.y_cord=y;
        TouchDataObj.major_axis=major_Axis;
        TouchDataObj.minor_axis=minor_axis;
        mtouchDataDao.insertOnlySingleTouchMetrics(TouchDataObj);
        Log.i("CS_BiA_TP_T","End");
    }
//    @Override
//    public void insertTouchTypeEntryBatch(TouchData[] multi_entry){
//        DBINSTANCE.TouchDataDao().insertMultipleTouchMetrics(multi_entry);
//    }

    @Override
    public TouchData[] fetchTouchDataRows(long keyId,int motioneventtype){
        return DBINSTANCE.TouchDataDao().fetchTouchData(keyId,motioneventtype);
    }
    @Override
    public  void insertSessionStartTime (long startTime){
        Log.i("CS_BiAffect_S_T","startTime -> "+startTime);
        SessionData sessionDataObj =new SessionData();
        sessionDataObj.startTime=startTime;
        sessionDataObj.endTime=startTime;
        DBINSTANCE.SessionDataDao().insertSessionStartTime(sessionDataObj);
        Log.i("CS_BiAffect_S_T","Inserted in db");
    }

    @Override
    public void updateSessionEndTime(long startTime,long endTime){
        SessionData sessionDataObj =new SessionData();
        sessionDataObj.startTime=startTime;
        sessionDataObj.endTime=endTime;
        DBINSTANCE.SessionDataDao().insertSessionStartTime(sessionDataObj);
    }

    @Override
    public void insertKeyTypeData (long keyId,int keytypecode,float x,float y, float width, float height){
        KeyData keyDataObj =new KeyData();
        keyDataObj.eventDownTime=keyId;
        keyDataObj.keyType=keytypecode;
        keyDataObj.Key_X=x;
        keyDataObj.Key_X=y;
        keyDataObj.Key_width=width;
        keyDataObj.Key_height=height;
        DBINSTANCE.KeyDataDAO().insertOnlySingleKeyMetrics(keyDataObj);
    }

//    @Override
//    public void insertMultipleKeyMetrics (KeyData[] keyDataList){
//        DBINSTANCE.KeyDataDAO().insertMultipleKeyMetrics(keyDataList);
//    }

    @Override
    public int updateRadiusofTouch(KeyData data){
        return DBINSTANCE.KeyDataDAO().updateRadiusofTouch(data);
    }

    @Override
    public void insertDeviceData (DeviceData single_entry){
        DBINSTANCE.DeviceDataDAO().insertDeviceData(single_entry);
    }

    @Override
    public int fetchDeviceId(){
        return DBINSTANCE.DeviceDataDAO().fetchDeviceId();
    }

    @Override
    public void insertOnlySingleAccelerometerEntry (AccelerometerData single_entry){
        DBINSTANCE.AccelDataDAO().insertOnlySingleAccelerometerEntry(single_entry);
    }

    @Override
    public void insertMultipleAccelerometerEntry (AccelerometerData[] DataList){
        DBINSTANCE.AccelDataDAO().insertMultipleAccelerometerEntry(DataList);
    }
}
