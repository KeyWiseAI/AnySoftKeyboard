package com.menny.android.anysoftkeyboard.BiAffect;

public interface BiADataProcessorInterface {
    boolean recordKeyPressForce(long eventDownTime, double pressure, int action) throws InterruptedException;

}
