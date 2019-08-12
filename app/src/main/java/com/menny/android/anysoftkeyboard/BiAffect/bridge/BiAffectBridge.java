package com.menny.android.anysoftkeyboard.BiAffect.bridge;

import android.text.TextUtils;

import org.sagebionetworks.bridge.android.manager.BridgeManagerProvider;
import org.sagebionetworks.bridge.rest.model.UserSessionInfo;

import rx.Single;

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
     * Temporary method to repeat the given boolean for testing purposes
     * @deprecated Replace with isUserLoggedIn()
     * @param loggedIn Boolean to return
     * @return the given loggedIn parameter
     */
    public boolean isUserLoggedIn( boolean loggedIn ) {
        return loggedIn;
    }

    /**
     * Checks to see if user is considered logged in or not.
     */
    public boolean isUserLoggedIn() {
        return null != BridgeManagerProvider.getInstance().getAuthenticationManager().getUserSessionInfo();
    }

    /**
     * Temporary method to emulate log in functionality.
     * @deprecated replace with Single logIn( email, password )
     * @return true if password is only digits, false otherwise.
     */
    public boolean login( String username, String password ) {
        if( TextUtils.isDigitsOnly( password ) ) {
            return true;
        }
        return false;
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

}
