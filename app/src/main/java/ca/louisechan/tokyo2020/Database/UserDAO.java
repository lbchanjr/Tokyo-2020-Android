package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.User;

@Dao
public interface UserDAO {
    // Insert record into database
    @Insert
    public void addUser(User user);

    // delete from database
    @Delete
    public void deleteUser(User user);

    @Update
    public void updateUser(User user);

    @Query("Select * from users")
    public List<User> getallUsers();

    @Query("SELECT * FROM users " +
            "WHERE id = :id ")
    public List<User> getUserWithID(int id);

    @Query("SELECT * FROM users " +
            "WHERE email = :email ")
    public List<User> getUserByEmail(String email);
}
