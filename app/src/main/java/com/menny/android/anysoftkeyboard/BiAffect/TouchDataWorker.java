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
            /**
             * Created by Sreetama Banerjee on 4/22/2019.
             * reason : to allow all components of project to get appcontext
             */
            Context context = AnyApplication.getAppContext();
            //Context context = sharedInstance.mContext;


            /**
             * Created by Sreetama Banerjee on 4/22/2019.
             * reason : getting instance of database manager class
             */
            BiAffectDBManager INSTANCE= BiAManager.getDBMngrInstance();

            //database entity instances
            /**
             * Created by Sreetama Banerjee on 4/22/2019.
             * reason : creating instance of TouchData entity
             *///
            /**
             * Created by Sreetama Banerjee on 4/22/2019.
             * reason : creating instance of TouchData entity array
             *///
            //TouchData[] touchMetricsList=new TouchData[temp.length];


            /**
             * Created by Sreetama Banerjee on 4/22/2019.
             * reason : implementation of pushing single rows into DB
             */
            TouchData touchMetrics;
            for(int i=0; i<temp.length; i++) {
                if (temp[i].used && temp[i].validatePOJO()) {
                    //Ready to be used inside the code
                    //Need to implement the method to push eveything into the db
                    touchMetrics = new TouchData();

                    touchMetrics.eventDownTime = temp[i].eventDownTime;
                    touchMetrics.eventTime = temp[i].eventTime;
                    touchMetrics.eventAction = temp[i].eventAction;
                    touchMetrics.pressure = temp[i].pressure;
                    touchMetrics.x_cord = temp[i].x_cord;
                    touchMetrics.y_cord = temp[i].y_cord;
                    touchMetrics.major_axis = temp[i].major_axis;
                    touchMetrics.minor_axis = temp[i].minor_axis;
                    //accelerometer values stores seaparately
//                    touchMetrics.accelerometer_x = temp[i].accelerometer_x;
//                    touchMetrics.accelerometer_y = temp[i].accelerometer_y;
//                    touchMetrics.accelerometer_z = temp[i].accelerometer_z;
//                    touchMetrics.touches = temp[i].touches;

                    /**
                     * Created by Sreetama Banerjee on 4/22/2019.
                     * reason : getting DAO instance and calling insert query
                     */
                    INSTANCE.insertTouchTypeEntry(touchMetrics);
                    temp[i].markUnused();
                }

                }

            /* COMMENTING FOR NOW */
//                /**
//                 * Created by Sreetama Banerjee on 4/22/2019.
//                 * reason : implementation of pushing multiple rows into DB
//                 */
//                for(int j=0; j<temp.length; j++){
//                    if(temp[j].used && temp[j].validatePOJO()){
//                        //Ready to be used inside the code
//                        //Need to implement the method to push eveything into the db
//
//                        touchMetrics.eventDownTime=temp[j].eventDownTime;
//                        touchMetrics.eventTime=temp[j].eventTime;
//                        touchMetrics.eventAction=temp[j].eventAction;
//                        touchMetrics.pressure=temp[j].pressure;
//                        touchMetrics.x_cord=temp[j].x_cord;
//                        touchMetrics.y_cord=temp[j].y_cord;
//                        touchMetrics.major_axis=temp[j].major_axis;
//                        touchMetrics.minor_axis=temp[j].minor_axis;
//                        touchMetrics.accelerometer_x=temp[j].accelerometer_x;
//                        touchMetrics.accelerometer_y=temp[j].accelerometer_y;
//                        touchMetrics.accelerometer_z=temp[j].accelerometer_z;
//                        touchMetrics.touches=temp[j].touches;
//
//
//                        touchMetricsList[j]=touchMetrics;
//
//
//                        temp[j].markUnused();
//                    }
//
//                }
//                /**
//                 * Created by Sreetama Banerjee on 4/22/2019.
//                 * reason : getting DAO instance and calling insert query (multiple batch insert)
//                 */
//                INSTANCE.insertTouchTypeEntryBatch(touchMetricsList);


        }catch (InterruptedException e){

        }finally {
            temp_Sempaphore.release();
            Log.i("CS_BiAffect","-----------BUFFER EMPTY END-------------"+this.bucket1);

            // just to check if data is being stored in db
            //Log.i("CS_BiAffect_DB_data",BiAffectDB.getDatabase(AnyApplication.getAppContext()).TouchDao().fetchTouchDataAll().toString());

        }
    }
}
