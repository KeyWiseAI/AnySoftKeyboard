package com.menny.android.anysoftkeyboard.BiAffectDB;

import android.content.Context;

import com.menny.android.anysoftkeyboard.AnyApplication;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.TouchData;

public class BiAffectDBManager implements BiAffectDBInterface.TouchDataInterface{
    /**
     * Created by Sreetama Banerjee on 4/22/2019.
     * reason : to allow all components of project to get appcontext
     */
    Context mcontext = AnyApplication.getAppContext();

    private BiAffectDB DBINSTANCE;

    private static BiAffectDBManager MngrInstance = null;

    public static synchronized BiAffectDBManager getInstance() {
            if (MngrInstance == null)
                MngrInstance = new BiAffectDBManager();
            return MngrInstance;
        }

    //can put static if need be
     private BiAffectDBManager() {
        DBINSTANCE=BiAffectDB.getDatabase(mcontext);

    }

    @Override
    public void insertTouchTypeEntry(TouchData single_entry){
        DBINSTANCE.TouchDao().insertOnlySingleTouchMetrics(single_entry);
    }
    @Override
    public void insertTouchTypeEntryBatch(TouchData[] multi_entry){
        DBINSTANCE.TouchDao().insertMultipleTouchMetrics(multi_entry);
    }
    @Override
    public TouchData[] fetchTouchDataRows(long keyId,int motioneventtype){
        return DBINSTANCE.TouchDao().fetchTouchData(keyId,motioneventtype);
    }
}
