package com.menny.android.anysoftkeyboard.BiAffect;

import android.content.Context;
import android.util.Log;

import com.menny.android.anysoftkeyboard.AnyApplication;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDBManager;
import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.TouchData;

import java.util.concurrent.Semaphore;

public class TouchDataWorker implements Runnable {
    boolean bucket1;
    BiAManager sharedInstance;
    TouchDataWorker(boolean bucket1){
        super();
        this.bucket1 = bucket1;
    }

    @Override
    public void run() {
        sharedInstance = BiAManager.getInstance(null);
        TouchDataPOJO[] temp;
        Semaphore temp_Sempaphore;
        if(bucket1){
            temp = sharedInstance.t1;
            temp_Sempaphore = sharedInstance.t1_Sempahore;
        }else{
            temp = sharedInstance.t2;
            temp_Sempaphore = sharedInstance.t2_Sempahore;
        }

        try {
            temp_Sempaphore.acquire();
            Log.i("CS_BiAffect","-----------BUFFER EMPTY START-------------"+this.bucket1);
            for(int i=0; i<temp.length; i++) {
                if (temp[i].used && temp[i].validatePOJO()) {
                    //Ready to be used inside the code
                    BiAffectDBManager sharedDbManager = BiAffectDBManager.getInstance();
                    sharedDbManager.insertTouchTypeData(temp[i].eventDownTime, temp[i].eventTime, temp[i].eventAction, temp[i].pressure, temp[i].x_cord, temp[i].y_cord, temp[i].major_axis, temp[i].minor_axis);
                    temp[i].markUnused();
                }
            }
        }catch (InterruptedException e){

        }finally {
            temp_Sempaphore.release();
            Log.i("CS_BiAffect","-----------BUFFER EMPTY END-------------"+this.bucket1);

            // just to check if data is being stored in db
            //Log.i("CS_BiAffect_DB_data",BiAffectDB.getDatabase(AnyApplication.getAppContext()).TouchDao().fetchTouchDataAll().toString());

        }
    }
}
