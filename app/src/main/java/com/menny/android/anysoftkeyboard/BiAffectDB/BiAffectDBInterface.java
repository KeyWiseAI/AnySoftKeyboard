package com.menny.android.anysoftkeyboard.BiAffectDB;

import com.menny.android.anysoftkeyboard.BiAffectDB.BiAffectDB_roomModel.TouchData;

public interface BiAffectDBInterface {
    interface TouchDataInterface {
        //All the calls to DAO for Touch entity will be processed.

        void insertTouchTypeEntry(TouchData single_entry);
        void insertTouchTypeEntryBatch(TouchData[] multi_entry);
        TouchData[] fetchTouchDataRows(long keyId,int motioneventtype);

    }

}
