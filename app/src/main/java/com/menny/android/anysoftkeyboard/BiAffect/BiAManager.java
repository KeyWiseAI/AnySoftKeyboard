package com.menny.android.anysoftkeyboard.BiAffect;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class BiAManager implements BiADataProcessorInterface {

    private BiAWorker1 myCurrentWorker1;

    //Instead of inner static class we can also use enum
    static class FeatureLookupStruct{
        final static String pressure = "PRESSURE";
        //final static String action_key_up = "Up";
        //final static String action_key_down = "Down";
    }

    private static BiAManager shared_instance = null;
    ArrayBlockingQueue<BiAFeature> myTupleQueue;
    LinkedHashMap<Long, BiAPOJO> processingMap;
    LinkedHashMap<Long, BiAPOJO> finalPOJOMap;


    private BiAManager(){
        //This wont contain anything as such
        this.myTupleQueue = new ArrayBlockingQueue<>(10000);
        this.processingMap = new LinkedHashMap<>();
        this.finalPOJOMap = new LinkedHashMap<>();
    }

    public static synchronized BiAManager getInstance()
    {
        if (shared_instance == null)
            shared_instance = new BiAManager();

        return shared_instance;
    }

    @Override
    public boolean recordKeyPressForce(long eventDownTime, double pressure, int action) throws NullPointerException, InterruptedException{
        System.out.println("BiAffect recordKeyPressForce received for "+eventDownTime+ " For action "+ action);
        Pressure myPressure = new Pressure(FeatureLookupStruct.pressure, eventDownTime, pressure, action);
        shared_instance.myTupleQueue.put(myPressure);

        return true;
    }

    //Session specific calls
    public boolean startSession(){
        myCurrentWorker1 = new BiAWorker1();
        //not sure if i can start the worker thread here
        myCurrentWorker1.start();
        return true;
    }

    public boolean endSession(){

        return true;
    }
}