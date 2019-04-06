package com.menny.android.anysoftkeyboard.BiAffect;
import android.util.Log;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.concurrent.ArrayBlockingQueue;


public class BiAWorker1 extends Thread{

    private boolean sessionRunning;

    private long sessionStartTime;

    BiAWorker1(){
        super();
        System.out.println("Session Started with start time to be "+sessionStartTime);
        this.sessionStartTime = System.currentTimeMillis();
        sessionRunning = true;
    }


    @Override
    public void run(){
        BiAManager managerSharedInstance = BiAManager.getInstance();
        ArrayBlockingQueue<BiAFeature> myQueueReference = managerSharedInstance.myTupleQueue;
        LinkedHashMap<Long, BiAPOJO> myProcessingMapReference = managerSharedInstance.processingMap;
        //Hashtable<Long, BiAPOJO> myFinalPOJOMapReference = managerSharedInstance.finalPOJOMap;

        BiAPOJO referenceHolder;

        while (sessionRunning){
            Log.d("BiA Data Processing", "BiAWorker1 is running");
            try {
                BiAFeature currentTuple = myQueueReference.take();

                if(myProcessingMapReference.containsKey(currentTuple.id)){
                    //already present in the map we dont need to create a new pojo instance
                    System.out.println("BiAffect Pojo already present in map");
                    referenceHolder = myProcessingMapReference.get(currentTuple.id);
                    assert referenceHolder != null;
                    referenceHolder.addRecord(currentTuple);
                }else{
                    //create pojo instance
                    System.out.println("BiAffect Creating new pojo");
                    referenceHolder = new BiAPOJO();
                    referenceHolder.addRecord(currentTuple);
                    myProcessingMapReference.put(currentTuple.id, referenceHolder);
                }

                if(!referenceHolder.totalRecordMap.containsValue(false)){
                    //Now this means that this pojo is ready to be pushed into second buffer
                    System.out.println("BiAffect A pojo just finished");
                }

            }catch (InterruptedException e) {
                //e.printStackTrace();
            }

            try {
                Thread.sleep(25);//sleep for 25ms
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }

    }

    public void endCurrentSession(){
        long sessionEndTime = System.currentTimeMillis();
        this.sessionRunning = false;
        System.out.println("Session Ended with end time to be "+ sessionEndTime);
    }
}

