package com.menny.android.anysoftkeyboard.BiAffect.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.TouchTypeData;

@Dao
public interface Touch_DAO {
    @Insert
    void insertSingleTouchDataEntry(TouchTypeData data);
}
