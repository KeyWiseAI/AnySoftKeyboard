package com.menny.android.anysoftkeyboard.BiAffect.bridge;

import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.AccelerometerData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.KeyTypeData;
import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.TouchTypeData;

import java.util.List;

import io.reactivex.Observable;

/**
 * JSON object that represents the correct data format that Bridge Research Manager expects for BiAffect.
 */
public class Session {
    private double              duration;
    private long                timestamp;
    private List<Keylogs>       keylogs;
    private List<Accelerations> accelerations;

    /**
     * Basic constructor for this class that initializes the required time-related parameters
     *
     * @param start epoch representing the start of the Session
     * @param end   epoch representing the end of the Session
     */
    public Session( long start, long end ) {
        duration = end - start;
        timestamp = start;
    }

    /**
     * Adds the correct Accelerations data from the AccelerometerData from the DB
     *
     * @param accelerometerData List of AccelerometerData associated with this Session
     */
    public void addAccelerometerData( List<AccelerometerData> accelerometerData ) {
        accelerations = Observable.fromIterable( accelerometerData )
                                  .map( Accelerations::new )
                                  .toList()
                                  .blockingGet();
    }

    /**
     * Adds the correct Keylogs data from the KeyTypeData from the DB
     *
     * @param keyTypeData List of KeyTypeData associated with this Session
     */
    public void addKeyTypeData( List<KeyTypeData> keyTypeData ) {
        keylogs = Observable.fromIterable( keyTypeData )
                            .map( Keylogs::new )
                            .toList()
                            .blockingGet();
    }

    /**
     * Adds the correct Keylogs data from the TouchTypeData from the DB
     *
     * @param touchTypeData List of TouchTypeData associated with this Session
     */
    public void addTouchTypeData( List<TouchTypeData> touchTypeData ) {
        keylogs = Observable.merge( Observable.fromIterable( keylogs ),
                                    Observable.fromIterable( touchTypeData )
                                              .map( Keylogs::new ) )
                            .toList()
                            .blockingGet();
    }

    /**
     * Class that maps to Keylogs in the scheme, but contains both KeyTypeData and TouchTypeData
     */
    private static class Keylogs {
        private KeyTypeData   keyTypeData;
        private TouchTypeData touchTypeData;

        private Keylogs( KeyTypeData keyTypeData ) {
            this.keyTypeData = keyTypeData;
        }

        private Keylogs( TouchTypeData touchTypeData ) {
            this.touchTypeData = touchTypeData;
        }
    }

    /**
     * Class that maps to Accelerations in the scheme
     */
    private static class Accelerations {
        private long   time;
        private double x;
        private double y;
        private double z;

        private Accelerations( AccelerometerData source ) {
            time = source.time;
            x = source.Acc_X;
            y = source.Acc_Y;
            z = source.Acc_Z;
        }
    }

}
