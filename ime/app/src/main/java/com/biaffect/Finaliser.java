package com.biaffect;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.sagebionetworks.bridge.android.manager.BridgeManagerProvider;
import org.sagebionetworks.bridge.data.Archive;
import org.sagebionetworks.bridge.data.JsonArchiveFile;

import java.util.List;
import java.util.Locale;

import com.biaffect.bridge.Session;
import com.biaffect.database.BiADatabaseManager;
import com.biaffect.database.models.DeviceData;
import com.biaffect.database.models.KeyTypeData;
import com.biaffect.database.models.TouchTypeData;
import rx.Subscription;

public class Finaliser implements Runnable {

    private boolean _insertsDone = false;

    private static String _appVersion;
    private static String _phoneInfo;

    long startTime;
    long endTime;
    BiAManager         sharedInstance;
    int                count=0;
    BiADatabaseManager mBiADatabaseManager;

    private Subscription uploadSubscription;

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

            int max = BiAManager.KEY_BUFFER_SIZE > BiAManager.TOUCH_BUFFER_SIZE ?
                      BiAManager.KEY_BUFFER_SIZE : BiAManager.TOUCH_BUFFER_SIZE;

            BiADatabaseManager.runInTransaction( () -> {
                synchronized( this ) {
                    for( int i = 0; i < max; i++ ) {
                        if( i < BiAManager.KEY_BUFFER_SIZE ) {
                            if( sharedInstance.k1[i].used ) {
                                KeyDataPOJO k = sharedInstance.k1[i];
                                mBiADatabaseManager.insertKeyData( k.eventDownTime,
                                                                   k.keyType,
                                                                   k.keyCentre_X,
                                                                   k.keyCentre_Y,
                                                                   k.keyWidth,
                                                                   k.keyHeight );
                                k.markUnused();
                                count++;
                            }
                            if( sharedInstance.k2[i].used ) {
                                KeyDataPOJO k = sharedInstance.k2[i];
                                mBiADatabaseManager.insertKeyData( k.eventDownTime,
                                                                   k.keyType,
                                                                   k.keyCentre_X,
                                                                   k.keyCentre_Y,
                                                                   k.keyWidth,
                                                                   k.keyHeight );
                                k.markUnused();
                                count++;
                            }
                        }

                        if( i < BiAManager.TOUCH_BUFFER_SIZE ) {
                            if( sharedInstance.t1[i].used ) {
                                TouchDataPOJO data = sharedInstance.t1[i];
                                mBiADatabaseManager.insertTouchData( data.eventDownTime,
                                                                     data.eventTime,
                                                                     data.eventAction,
                                                                     data.pressure,
                                                                     data.x_cord,
                                                                     data.y_cord,
                                                                     data.major_axis,
                                                                     data.minor_axis );
                                data.markUnused();
                                count++;
                            }
                            if( sharedInstance.t2[i].used ) {
                                TouchDataPOJO data = sharedInstance.t2[i];
                                mBiADatabaseManager.insertTouchData( data.eventDownTime,
                                                                     data.eventTime,
                                                                     data.eventAction,
                                                                     data.pressure,
                                                                     data.x_cord,
                                                                     data.y_cord,
                                                                     data.major_axis,
                                                                     data.minor_axis );
                                data.markUnused();
                                count++;
                            }
                        }
                    }
                    _insertsDone = true;
                    notifyAll();
                }
                mBiADatabaseManager.updateSessionData( startTime, endTime );

            } );

            while( !_insertsDone ) {
                synchronized( this ) {
                    wait();
                }
            }

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
        uploadSubscription = upload();
    }

    /**
     * Creates a Session object that represents this session that just got finalized.
     * Queues the upload of the Session object.
     * Takes care of unsubscribing correctly from the upload queue.
     *
     * @return Subscription that needs to be referenced by uploadSubscription in order to support unsubscribing
     */
    private Subscription upload() {
        List<KeyTypeData> keys = mBiADatabaseManager.getKeyTypeData( startTime, endTime );
        if( keys.size() < 1 ) {
            return null;
        }

        Session session = new Session( startTime, endTime );

        try { //we want to wait for 100ms because that's the delay for AccelerometerDataWorker logic.
            Thread.sleep( 100 );
        } catch( InterruptedException ignore ) {}

        session.addAccelerometerData( mBiADatabaseManager.getAccelerometerData( startTime, endTime ) );

        for( KeyTypeData key : keys ) {
            Session.Keylog keylog = new Session.Keylog( key);
            for( TouchTypeData touch : BiADatabaseManager.getTouchTypeData( key.keyDownTime_id ) ) {
                keylog.addTouch( touch );
            }
            session.addKeylog( keylog );
        }

        Archive.Builder builder = Archive.Builder.forActivity( "KeyboardSession" );
        builder.addDataFile( new JsonArchiveFile( "Session.json",
                                                  new DateTime( endTime ),
                                                  new Gson().toJson( session ) ) );

        if( TextUtils.isEmpty( _appVersion ) ) {
            try {
                Context context = BridgeManagerProvider.getInstance().getApplicationContext();
                PackageInfo info = context.getPackageManager()
                                          .getPackageInfo( context.getPackageName(),
                                                           0 );
                _appVersion = String.format( Locale.ENGLISH,
                                             "%s(%d)",
                                             info.versionName,
                                             info.versionCode );
            } catch( PackageManager.NameNotFoundException ignore ) {
            } finally {
                if( TextUtils.isEmpty( _appVersion ) ) {
                    builder.withAppVersionName( "Unknown" );
                } else {
                    builder.withAppVersionName( _appVersion );
                }
            }
        } else {
            builder.withAppVersionName( _appVersion );
        }

        if( TextUtils.isEmpty( _phoneInfo ) ) {
            DeviceData deviceData = mBiADatabaseManager.getDeviceData();
            _phoneInfo = String.format( Locale.ENGLISH,
                                        "%s %s %s|dp:%s dpi:%s w:%s h:%s",
                                        deviceData.manufacturer,
                                        deviceData.phoneModel,
                                        deviceData.androidVersion,
                                        deviceData.pixelDensityLogical,
                                        deviceData.pixelDensityDpi,
                                        deviceData.deviceWidthPixel,
                                        deviceData.deviceHeightPixel );
        }
        builder.withPhoneInfo( _phoneInfo );

        return BridgeManagerProvider.getInstance()
                                    .getUploadManager()
                                    .queueUpload( String.valueOf( startTime ), builder.build() )
                                    .subscribe(
                                        success -> {
                                            Log.d( "Bridge SDK Upload", "upload queued successfully" );
                                            if( null != uploadSubscription ) {
                                                uploadSubscription.unsubscribe();
                                            }
                                        }, error -> {
                                            error.printStackTrace();
                                            if( null != uploadSubscription ) {
                                                uploadSubscription.unsubscribe();
                                            }
                                        } );
    }

}
