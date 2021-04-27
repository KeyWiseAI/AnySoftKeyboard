package com.biaffect.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.biaffect.database.dao.Accelerometer_DAO;
import com.biaffect.database.dao.Device_DAO;
import com.biaffect.database.dao.Key_DAO;
import com.biaffect.database.dao.Session_DAO;
import com.biaffect.database.dao.Touch_DAO;
import com.biaffect.database.models.AccelerometerData;
import com.biaffect.database.models.DeviceData;
import com.biaffect.database.models.KeyTypeData;
import com.biaffect.database.models.SessionData;
import com.biaffect.database.models.TouchTypeData;

/*
Room class must be abstract and extend RoomDatabase. This class is the boiler plate for the database to be created
Usually, you only need one instance of the Room database for the whole app.
*/

//Annotate the class to be a Room database, declare the entities that belong in the database and set the version number.
// Listing the entities will create tables in the database.
@Database( entities = { SessionData.class,
                        TouchTypeData.class,
                        KeyTypeData.class,
                        AccelerometerData.class,
                        DeviceData.class },
           version = 1,
           exportSchema = false )
public abstract class BiAffect_Database extends RoomDatabase {

    // Defining the DAOs that work with the database.
    // Since gave all of the DAOs public access, no need to explicity provide abstract "getter" method for each @Dao.
    // Definition required in this class so that Room knows which transactions should be handled for each table.
    public abstract Session_DAO mSession_dao();
    public abstract Touch_DAO mTouch_dao();
    public abstract Key_DAO mKey_dao();
    public abstract Accelerometer_DAO mAccelerometer_dao();
    public abstract Device_DAO mDevice_dao();

}
