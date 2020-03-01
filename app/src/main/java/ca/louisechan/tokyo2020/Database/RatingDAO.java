package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.Rating;
import ca.louisechan.tokyo2020.Models.RatingByName;

@Dao
public interface RatingDAO {
    // Insert record into database
    @Insert
    void addRating(Rating r);

    // delete from database
    @Delete
    void deleteRating(Rating r);

    // delete table contents
    @Query("DELETE FROM ratings")
    void deleteAllRatings();

    @Update
    void updateRating(Rating r);

    @Query("Select * from ratings")
    List<Rating> getallRatings();

    @Query("SELECT * FROM ratings " +
            "WHERE id = :id ")
    List<Rating> getRatingByID(int id);

    @Query("SELECT * FROM ratings " +
            "WHERE attraction_name = :attraction ")
    List<Rating> getRatingsByAttraction(String attraction);

    @Query("SELECT * FROM ratings " +
            "WHERE user_email = :email ")
    List<Rating> getRatingsByUserEmail(String email);

    @Query("SELECT u.name, r.attraction_name, rating FROM ratings AS r " +
            "INNER JOIN users AS u ON u.email = r.user_email")
    List<RatingByName> getRatingsWithNameList();
}
