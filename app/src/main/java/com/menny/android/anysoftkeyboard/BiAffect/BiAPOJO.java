package com.menny.android.anysoftkeyboard.BiAffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BiAPOJO{
    public HashMap<FeatureSet, Boolean> totalRecordMap;
    public List<Double> pressureValues;
    public double finalPressure;

    public enum FeatureSet{
        PRESSURE
    }

    public BiAPOJO(){
        this.totalRecordMap = new HashMap<>();
        for(FeatureSet f : FeatureSet.values()){
            totalRecordMap.put(f,false);
        }
        pressureValues = new ArrayList<>();
    }

    public void addRecord(BiAFeature feature){
        //we will have switch statement for tag of the feature
        System.out.println("BiAffect, I am from Pojo, something changed in me");
        switch (feature.TAG){
            case BiAManager.FeatureLookupStruct.pressure:
                //this is pressure value, we need to call pressure function and let it do the rest
                Pressure currentInstance = (Pressure) feature;
                recordPressure(currentInstance.action, currentInstance.value);

        }
    }

    public void recordPressure(int action, double value){
        pressureValues.add(value);
        System.out.println("BiAffect, recorded pressure value is "+value);
        if(action == 1){
            //This means this was ACTION_UP, we need to call finalize pressure
            finalizePressure();
        }
    }

    private void finalizePressure(){
        //We can traverse through the list and get max or min or avg or whatever required
        System.out.println("BiAffect, Pressure got finalised");
        this.finalPressure = Collections.max(pressureValues);
        System.out.println("BiAffect, Final pressure value is "+ this.finalPressure);
        totalRecordMap.remove(FeatureSet.PRESSURE);
        totalRecordMap.put(FeatureSet.PRESSURE, true);

    }

    public void printAllValues(){

    }
}

