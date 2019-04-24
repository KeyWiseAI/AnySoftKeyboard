package com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.TouchData;
/**
 * Created by Sreetama Banerjee on 4/22/2019.
 * reason : room DAO
 */

@Dao
public interface Touch_DAO {

    @Insert
    void insertOnlySingleTouchMetrics (TouchData single_entry);
    @Insert
    void insertMultipleTouchMetrics (TouchData[] touchDataList);

    //will be required to fetch X Y coords during Key data update
    @Query("SELECT * FROM TouchTypeData WHERE  KeyId= :keyId"+" AND Key_Event_Action= :motioneventtype")
    public TouchData[] fetchTouchData(long keyId,int motioneventtype);

}
