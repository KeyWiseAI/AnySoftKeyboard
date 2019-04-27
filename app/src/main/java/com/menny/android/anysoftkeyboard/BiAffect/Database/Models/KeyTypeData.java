package com.menny.android.anysoftkeyboard.BiAffect.Database.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class KeyTypeData {
    @PrimaryKey
    @NonNull
    public long keyDownTime_id;

    @NonNull
    public int keyTypeCode;

    @NonNull
    public float keyCentre_X;

    @NonNull
    public float keyCentre_Y;

    @NonNull
    public float key_Width;

    @NonNull
    public float key_Height;
}
