package com.menny.android.anysoftkeyboard.BiAffect;

import android.util.Log;

import com.menny.android.anysoftkeyboard.BiAffectDB_roomModel.TouchData;
import com.menny.android.anysoftkeyboard.BiAffectDatabase;

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
        sharedInstance = BiAManager.getInstance();
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
            // creating instance of TouchData entity
            TouchData touchMetrics = new TouchData();
            for(int i=0; i<temp.length; i++){
                if(temp[i].used && temp[i].validatePOJO()){
                    //Ready to be used inside the code
                    //Need to implement the method to push eveything into the db

                    /*
                    * implementation of pushing single rows into DB*/
                    touchMetrics.eventDownTime=temp[i].eventDownTime;
                    touchMetrics.eventTime=temp[i].eventTime;
                    touchMetrics.eventAction=temp[i].eventAction;
                    touchMetrics.pressure=temp[i].pressure;
                    touchMetrics.x_cord=temp[i].x_cord;
                    touchMetrics.y_cord=temp[i].y_cord;
                    touchMetrics.major_axis=temp[i].major_axis;
                    touchMetrics.minor_axis=temp[i].minor_axis;
                    touchMetrics.accelerometer_x=temp[i].accelerometer_x;
                    touchMetrics.accelerometer_y=temp[i].accelerometer_y;
                    touchMetrics.accelerometer_z=temp[i].accelerometer_z;
                    touchMetrics.touches=temp[i].touches;
                    BiAffectDatabase.TouchDao().insertOnlySingleMovie();

                    temp[i].markUnused();
                }
            }
        }catch (InterruptedException e){

        }finally {
            temp_Sempaphore.release();
            Log.i("CS_BiAffect","-----------BUFFER EMPTY END-------------"+this.bucket1);
        }
    }
}
