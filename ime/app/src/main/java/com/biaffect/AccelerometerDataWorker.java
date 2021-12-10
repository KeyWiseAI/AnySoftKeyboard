package com.biaffect;

import com.biaffect.database.BiADatabaseManager;

public class AccelerometerDataWorker implements Runnable {
    BiAManager         sharedInstance;
    BiADatabaseManager mBiADatabaseManager;
    final int FREQUENCY = 10;
    final int oneSecondMillis = 1000;

    public boolean stop;

    public AccelerometerDataWorker(BiADatabaseManager databaseManager){
        super();
        sharedInstance = BiAManager.getInstance( null);
        mBiADatabaseManager = databaseManager;
    }

    @Override
    public void run() {
        while(!stop){
            long currentTime = System.currentTimeMillis();
            mBiADatabaseManager.insertAccelerometerData( currentTime,
                                                         sharedInstance.current_accelerometer_x,
                                                         sharedInstance.current_accelerometer_y,
                                                         sharedInstance.current_accelerometer_z );
            //Log.i("ACC", "PUSHED");
            try {
                Thread.sleep(oneSecondMillis/FREQUENCY);
            }catch (InterruptedException e){
                //Do nothing
            }
        }
    }
}