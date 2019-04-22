import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface Touch_DAO {

    @Insert
    void insertOnlySingleMovie (TouchData single_entry);
    @Insert
    void insertMultipleMovies (TouchData[] touchDataList);
}
