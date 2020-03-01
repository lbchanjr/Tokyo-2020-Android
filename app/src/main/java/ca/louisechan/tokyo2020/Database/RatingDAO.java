package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.Rating;

@Dao
public interface RatingDAO {
    // Insert record into database
    @Insert
    public void addRating(Rating r);

    // delete from database
    @Delete
    public void deleteRating(Rating r);

    // delete table
    @Query("DELETE FROM ratings")
    public void deleteAllRatings();

    @Update
    public void updateRating(Rating r);

    @Query("Select * from ratings")
    public List<Rating> getallRatings();

    @Query("SELECT * FROM ratings " +
            "WHERE id = :id ")
    public List<Rating> getRatingByID(int id);

    @Query("SELECT * FROM ratings " +
            "WHERE attraction_name = :attraction ")
    public List<Rating> getRatingsByAttraction(String attraction);

    @Query("SELECT * FROM ratings " +
            "WHERE user_id = :id ")
    public List<Rating> getRatingsByUserID(int id);
}
