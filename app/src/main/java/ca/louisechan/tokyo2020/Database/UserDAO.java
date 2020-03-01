package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.User;

@Dao
public interface UserDAO {
    // Insert record into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addUser(User user);

    // delete from database
    @Delete
    void deleteUser(User user);

    // delete table contents
    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Update
    void updateUser(User user);

    @Query("Select * from users")
    List<User> getallUsers();

    @Query("SELECT * FROM users " +
            "WHERE email = :email ")
    List<User> getUserByEmail(String email);

}
