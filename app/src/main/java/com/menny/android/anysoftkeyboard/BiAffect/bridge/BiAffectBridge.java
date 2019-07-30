package com.menny.android.anysoftkeyboard.BiAffect.bridge;

import android.text.TextUtils;

public class BiAffectBridge {

    private static class Holder {
        public static BiAffectBridge instance = new BiAffectBridge();
    }

    private BiAffectBridge() {}

    public static BiAffectBridge getInstance() {
        return Holder.instance;
    }

    public boolean isUserLoggedIn( boolean loggedIn ) {
        return loggedIn;
    }

    public boolean login( String username, String password ) {
        if( TextUtils.isDigitsOnly( password ) ) {
            return true;
        }
        return false;
    }

}
