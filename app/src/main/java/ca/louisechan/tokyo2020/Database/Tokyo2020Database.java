package ca.louisechan.tokyo2020.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ca.louisechan.tokyo2020.Models.Attraction;
import ca.louisechan.tokyo2020.Models.Rating;
import ca.louisechan.tokyo2020.Models.User;
import ca.louisechan.tokyo2020.Models.Wishlist;

@Database(entities = {User.class, Attraction.class, Rating.class, Wishlist.class}, version = 3)
public abstract class Tokyo2020Database extends RoomDatabase {
    public abstract UserDAO userDao();
    public abstract AttractionDAO attractionDao();
    public abstract RatingDAO ratingDao();
    public abstract WishlistDAO wishlistDao();
}
