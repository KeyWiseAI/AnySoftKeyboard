package com.menny.android.anysoftkeyboard.BiAffect;

import android.util.Log;

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
            for(int i=0; i<temp.length; i++){
                if(temp[i].used && temp[i].validatePOJO()){
                    //Ready to be used inside the code
                    //Need to implement the method to push eveything into the db
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
