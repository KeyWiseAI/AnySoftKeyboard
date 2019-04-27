package com.menny.android.anysoftkeyboard.BiAffect;

import android.util.Log;

import com.menny.android.anysoftkeyboard.BiAffect.Database.BiADatabaseManager;
import com.menny.android.anysoftkeyboard.BiAffect.Database.BiAffect_Database;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.SessionData;

public class Finaliser implements Runnable {

    long startTime;
    long endTime;
    BiAManager sharedInstance;
    int count=0;
    BiADatabaseManager mBiADatabaseManager;

    public Finaliser(long startTime, long endTime, BiADatabaseManager databaseManager){
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.sharedInstance = BiAManager.getInstance(null);
        mBiADatabaseManager = databaseManager;
    }

    @Override
    public void run() {
        Log.i("CS_BiAffect_E_T","-----------FINALISER START-------------");
        try {
            //This acquires all the semaphores first
            sharedInstance.k1_Semaphore.acquire();
            sharedInstance.k2_Semaphore.acquire();
            sharedInstance.t1_Sempahore.acquire();
            sharedInstance.t2_Sempahore.acquire();

            int max = sharedInstance.KEY_BUFFER_SIZE > sharedInstance.TOUCH_BUFFER_SIZE?sharedInstance.KEY_BUFFER_SIZE:sharedInstance.TOUCH_BUFFER_SIZE;

            for(int i=0; i<max; i++){
                if(i<sharedInstance.KEY_BUFFER_SIZE){
                    if(sharedInstance.k1[i].used){
                        KeyDataPOJO k = sharedInstance.k1[i];
                        mBiADatabaseManager.insertKeyData(k.eventDownTime, k.keyType, k.keyCentre_X, k.keyCentre_Y, k.keyWidth, k.keyHeight);
                        k.markUnused();
                        count++;
                    }
                    if(sharedInstance.k2[i].used){
                        KeyDataPOJO k = sharedInstance.k2[i];
                        mBiADatabaseManager.insertKeyData(k.eventDownTime, k.keyType, k.keyCentre_X, k.keyCentre_Y, k.keyWidth, k.keyHeight);
                        k.markUnused();
                        count++;
                    }
                }

                if(i<sharedInstance.TOUCH_BUFFER_SIZE){
                    if(sharedInstance.t1[i].used){
                        TouchDataPOJO data = sharedInstance.t1[i];
                        mBiADatabaseManager.insertTouchData(data.eventDownTime, data.eventTime, data.eventAction, data.pressure, data.x_cord, data.y_cord, data.major_axis, data.minor_axis);
                        data.markUnused();
                        count++;
                    }
                    if(sharedInstance.t2[i].used){
                        TouchDataPOJO data = sharedInstance.t2[i];
                        mBiADatabaseManager.insertTouchData(data.eventDownTime, data.eventTime, data.eventAction, data.pressure, data.x_cord, data.y_cord, data.major_axis, data.minor_axis);
                        data.markUnused();
                        count++;
                    }
                }
            }
        mBiADatabaseManager.updateSessionData(startTime,endTime);

        }catch (InterruptedException e){

        }finally {
            //This releases all the semaphores in the end
            sharedInstance.k1_Semaphore.release();
            sharedInstance.k2_Semaphore.release();
            sharedInstance.t1_Sempahore.release();
            sharedInstance.t2_Sempahore.release();
            Log.i("CS_BiAffect_E_T","Count ->"+count);
            Log.i("CS_BiAffect_E_T","-----------FINALISER END-------------");

        }
    }
}
