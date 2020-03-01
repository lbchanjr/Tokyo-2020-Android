package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.Attraction;

@Dao
public interface AttractionDAO {
    // Insert record into database
    @Insert
    public void addAttraction(Attraction a);

    // delete from database
    @Delete
    public void deleteAttraction(Attraction a);

    // delete table
    @Query("DELETE FROM attractions")
    public void deleteAllAttractions();

    @Update
    public void updateAttraction(Attraction a);

    @Query("Select * from attractions")
    public List<Attraction> getallAttractions();

    @Query("SELECT * FROM attractions " +
            "WHERE name = :name ")
    public List<Attraction> getAttractionByID(String name);
}
