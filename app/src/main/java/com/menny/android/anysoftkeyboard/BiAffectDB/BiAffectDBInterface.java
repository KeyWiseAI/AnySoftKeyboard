package com.menny.android.anysoftkeyboard.BiAffectDB;


import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.AccelerometerData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.DeviceData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.KeyData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.SessionData;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.TouchData;

public interface BiAffectDBInterface {
    interface TouchDataInterface {
        void insertTouchTypeData(long eventDowntime,long eventTime,int eventAction,float pressure,float x, float y,float major_Axis,float minor_axis);
        //void insertTouchTypeEntryBatch(TouchData[] multi_entry);
        TouchData[] fetchTouchDataRows(long keyId,int motioneventtype);

    }

    interface SessionDataInterface {

        void insertSessionStartTime (long startTime);
        void updateSessionEndTime(long startTime,long endTime);
    }

    interface KeyTypeDataInterface {
        void insertKeyTypeData (long keyId,int keytypecode,float x,float y, float width, float height);

        //void insertMultipleKeyMetrics (KeyData[] keyDataList);

        //for radius one way of adding to DB will be update at end of session. Also time of key up. Creating the stub as placeholder for now
        //not required but kept return type int so that Roomreturns number of rows edited. This will help in debugging

        int updateRadiusofTouch(KeyData data);

    }

    interface DeviceDataInterface {
        void insertDeviceData (DeviceData single_entry);

        int fetchDeviceId();

    }

    interface AccelerometerDataInterface {
        void insertOnlySingleAccelerometerEntry (AccelerometerData single_entry);

        void insertMultipleAccelerometerEntry (AccelerometerData[] DataList);
    }

}
