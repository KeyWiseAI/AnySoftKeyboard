package com.menny.android.anysoftkeyboard.BiAffect.bridge;

import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.sagebionetworks.bridge.android.BridgeConfig;
import org.sagebionetworks.bridge.android.manager.BridgeManagerProvider;
import org.sagebionetworks.bridge.data.Archive;
import org.sagebionetworks.bridge.data.JsonArchiveFile;
import org.sagebionetworks.bridge.rest.model.UserSessionInfo;

import java.util.Locale;

import rx.Single;
import rx.functions.Action0;

/**
 * Utility class to help manage the connection between BiAffect data and the Sage Bridge SDK
 *
 * @author wang@arbormoon.com
 */
public class BiAffectBridge {

    private static class Holder {
        public static BiAffectBridge instance = new BiAffectBridge();
    }

    private BiAffectBridge() {}

    public static BiAffectBridge getInstance() {
        return Holder.instance;
    }

    /**
     * Checks to see if user is considered logged in or not.
     */
    public boolean isUserLoggedIn() {
        return null != BridgeManagerProvider.getInstance().getAuthenticationManager().getUserSessionInfo();
    }

    /**
     * Logs the user in to the Bridge Study Manager.
     * @param email Email that the user signed up with
     * @param password Password that the user signed up with
     * @return A Single that can be subscribed to for updates.
     */
    public Single<UserSessionInfo> logIn( String email, String password ) {
        return BridgeManagerProvider.getInstance().getAuthenticationManager().signIn( email, password );
    }


    public void upload() {
        String json = new Gson().toJson( new Session() );


        //this is found in upload schemas under upload&export
        // TODO: 8/15/2019 Figure out what exactly the endDate is.
        JsonArchiveFile file = new JsonArchiveFile("Session.json", DateTime.now(), json);
        Archive.Builder builder = Archive.Builder.forActivity( "KeyboardSession" );
        builder.addDataFile( file );

        BridgeConfig config = BridgeManagerProvider.getInstance().getBridgeConfig();
        String appVersionString = String.format(Locale.ENGLISH, "version %s, build %d",
                config.getAppVersionName(),
                config.getAppVersion());
        builder.withAppVersionName(appVersionString);
        builder.withPhoneInfo(config.getDeviceName());
        Archive archive = builder.build();

        BridgeManagerProvider.getInstance().getUploadManager().queueUpload( "UUID filename", archive ).subscribe( success -> {
            BridgeManagerProvider.getInstance().getUploadManager().processUploadFiles().subscribe(new Action0() {
                @Override
                public void call() {
                    Log.wtf("asf", "i'm complete??");
                }
            }, error -> {
                Log.wtf( "asdf", error);
            });
        }, error -> {
            Log.wtf("asdf", error.getMessage());
        });

        //Call this only when ready to upload files (maybe with work manager)
    }

}
