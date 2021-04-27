package com.biaffect.bridge;

import android.os.Build;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.sagebionetworks.bridge.android.manager.BridgeManagerProvider;
import org.sagebionetworks.bridge.rest.model.ConsentStatus;
import org.sagebionetworks.bridge.rest.model.SignUp;
import org.sagebionetworks.bridge.rest.model.UserSessionInfo;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Completable;
import rx.Single;

/**
 * Utility class to help manage the connection between BiAffect data and the Sage Bridge SDK
 *
 * @author wang@arbormoon.com
 */
public final class BiAffectBridge {

    private BiAffectBridge() {}

    /**
     * Checks to see if user is considered logged in or not.
     * Sets up Session uploads if the user is logged in.
     */
    public static boolean isUserLoggedIn() {
        boolean loggedIn = (null != BridgeManagerProvider.getInstance().getAuthenticationManager().getUserSessionInfo());
        if( loggedIn ) {
            setUpWorkManager();
        }
        return loggedIn;
    }

    /**
     * Sets up WorkManager so queued uploads will be processed:
     * once a day
     * when the device is on an unmetered connection
     * when the device is charging
     * when the device is idle
     */
    private static void setUpWorkManager() {
        WorkManager.getInstance().cancelAllWork();
        Constraints.Builder builder = new Constraints.Builder().setRequiredNetworkType( NetworkType.UNMETERED )
                                                               .setRequiresCharging( true );
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            builder.setRequiresDeviceIdle( true );
        }

        Constraints constraints = builder.build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder( BridgeWorker.class,
                                                                       1,
                                                                       TimeUnit.DAYS )
                                              .setConstraints( constraints )
                                              .build();

        WorkManager.getInstance().enqueue( request );
    }

    /**
     * Logs the user in to the Bridge Study Manager.
     * Successful login will also set up Session uploads.
     *
     * @param email    Email that the user signed up with
     * @param password Password that the user signed up with
     * @return A Single that can be subscribed to for updates.
     */
    public static Single<UserSessionInfo> logIn( String email, String password ) {
        return BridgeManagerProvider.getInstance()
                                    .getAuthenticationManager()
                                    .signIn( email, password )
                                    .doOnSuccess( __ -> setUpWorkManager() );
    }

    /**
     * Attempts to bypass the consent step to help facilitate onboarding.
     *
     * @param userSessionInfo UserSessionInfo to get the required consents from
     * @return A Single whose success represents a successful bypass
     */
    public static Single<UserSessionInfo> bypassConsent( UserSessionInfo userSessionInfo ) {
        Map<String, ConsentStatus> consents = userSessionInfo.getConsentStatuses();
        for( String guid : consents.keySet() ) {
            ConsentStatus consent = consents.get( guid );
            if( null == consent ) {
                continue;
            }
            if( consent.isRequired() && !consent.isConsented() ) {
                return BridgeManagerProvider.getInstance()
                                            .getAuthenticationManager()
                                            .giveConsent( guid,
                                                          userSessionInfo.getFirstName() + " " + userSessionInfo.getLastName(),
                                                          null,
                                                          null,
                                                          null,
                                                          userSessionInfo.getSharingScope() );
            }
        }
        return null;
    }

    /**
     * Signs the user up for the BiAffect project in Bridge Study Manager.
     *
     * @return A Completable that can be subscribed to for updates.
     */
    public static Completable signUp( SignUp signUp ) {
        return BridgeManagerProvider.getInstance()
                                    .getAuthenticationManager()
                                    .signUp( signUp );
    }

    public static Completable resendEmail( String email ) {
        return BridgeManagerProvider.getInstance()
                                    .getAuthenticationManager()
                                    .resendEmailVerification( email );
    }

    public static void singleUpload() {
        WorkManager.getInstance().cancelAllWork();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder( BridgeWorker.class ).build();
        WorkManager.getInstance().enqueue( request );
    }

}
