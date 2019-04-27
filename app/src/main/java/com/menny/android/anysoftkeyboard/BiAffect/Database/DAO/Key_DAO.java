package com.menny.android.anysoftkeyboard.BiAffect.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.menny.android.anysoftkeyboard.BiAffect.Database.Models.KeyTypeData;

@Dao
public interface Key_DAO {
    @Insert
    void insertSingleKeyData(KeyTypeData data);
}
