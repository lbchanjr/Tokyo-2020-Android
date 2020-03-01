package ca.louisechan.tokyo2020.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ca.louisechan.tokyo2020.Models.User;

@Database(entities = {User.class}, version = 2)
public abstract class Tokyo2020Database extends RoomDatabase {
    public abstract UserDAO userDao();
}
