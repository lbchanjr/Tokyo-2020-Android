package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.Attraction;

@Dao
public interface AttractionDAO {
    // Insert record into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addAttraction(Attraction a);

    // delete from database
    @Delete
    void deleteAttraction(Attraction a);

    // delete table contents
    @Query("DELETE FROM attractions")
    void deleteAllAttractions();

    @Update
    void updateAttraction(Attraction a);

    @Query("Select * from attractions")
    List<Attraction> getallAttractions();

    @Query("SELECT * FROM attractions " +
            "WHERE name = :name ")
    List<Attraction> getAttractionByName(String name);
}
