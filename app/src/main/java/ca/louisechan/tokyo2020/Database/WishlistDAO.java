package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.Wishlist;

@Dao
public interface WishlistDAO {
    // Insert record into database
    @Insert
    public void addWishlist(Wishlist w);

    // delete from database
    @Delete
    public void deleteWishlist(Wishlist w);

    // delete table
    @Query("DELETE FROM wishlists")
    public void deleteAllWishlists();

    @Update
    public void updateWishlist(Wishlist w);

    @Query("Select * from wishlists")
    public List<Wishlist> getallWishlists();

    @Query("SELECT * FROM wishlists " +
            "WHERE id = :id ")
    public List<Wishlist> getWishlistByID(int id);

    @Query("SELECT * FROM wishlists " +
            "WHERE attraction_name = :attraction ")
    public List<Wishlist> getWishlistsByAttraction(String attraction);

    @Query("SELECT * FROM wishlists " +
            "WHERE user_id = :id ")
    public List<Wishlist> getWishlistsByUserID(int id);

}
