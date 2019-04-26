package com.menny.android.anysoftkeyboard.BiAffect;

import android.util.Log;

import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDBManager;

public class Finaliser implements Runnable {

    long startTime;
    long endTime;
    BiAManager sharedInstance;
    int count=0;

    public Finaliser(long startTime, long endTime){
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.sharedInstance = BiAManager.getInstance(null);
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
                        KeyDataPOJO temp = sharedInstance.k1[i];
                        BiAffectDBManager.getInstance().insertKeyTypeData(temp.eventDownTime, temp.keyType, temp.keyCentre_X, temp.keyCentre_Y, temp.keyWidth, temp.keyHeight);
                        temp.printYourself();
                        temp.markUnused();
                        count++;
                    }
                    if(sharedInstance.k2[i].used){
                        KeyDataPOJO temp = sharedInstance.k2[i];
                        BiAffectDBManager.getInstance().insertKeyTypeData(temp.eventDownTime, temp.keyType, temp.keyCentre_X, temp.keyCentre_Y, temp.keyWidth, temp.keyHeight);
                        temp.printYourself();
                        temp.markUnused();
                        count++;
                    }
                }

                if(i<sharedInstance.TOUCH_BUFFER_SIZE){
                    if(sharedInstance.t1[i].used){
                        TouchDataPOJO temp = sharedInstance.t1[i];
                        BiAffectDBManager.getInstance().insertTouchTypeData(temp.eventDownTime, temp.eventTime, temp.eventAction, temp.pressure, temp.x_cord, temp.y_cord, temp.major_axis, temp.minor_axis);
                        temp.printYourself();
                        temp.markUnused();
                        count++;
                    }
                    if(sharedInstance.t2[i].used){
                        TouchDataPOJO temp = sharedInstance.t2[i];
                        BiAffectDBManager.getInstance().insertTouchTypeData(temp.eventDownTime, temp.eventTime, temp.eventAction, temp.pressure, temp.x_cord, temp.y_cord, temp.major_axis, temp.minor_axis);
                        temp.printYourself();
                        temp.markUnused();
                        count++;
                    }
                }
            }

        }catch (InterruptedException e){

        }finally {
            //This releases all the semaphores in the end
            sharedInstance.k1_Semaphore.release();
            sharedInstance.k2_Semaphore.release();
            sharedInstance.t1_Sempahore.release();
            sharedInstance.t2_Sempahore.release();
            BiAffectDBManager.getInstance().updateSessionEndTime(startTime, endTime);
            Log.i("CS_BiAffect_E_T","Count ->"+count);
            Log.i("CS_BiAffect_E_T","-----------FINALISER END-------------");

        }
    }
}
