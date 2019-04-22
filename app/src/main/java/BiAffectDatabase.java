import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TouchData.class,KeyData.class}, version = 1)
public abstract class BiAffectDatabase extends RoomDatabase {

    public abstract Touch_DAO TouchDao();

    private static volatile BiAffectDatabase INSTANCE;

    static BiAffectDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BiAffectDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BiAffectDatabase.class, "BiAffect_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
