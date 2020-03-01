package ca.louisechan.tokyo2020.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ca.louisechan.tokyo2020.Models.Wishlist;
import ca.louisechan.tokyo2020.Models.WishlistByName;

@Dao
public interface WishlistDAO {
    // Insert record into database
    @Insert
    void addWishlist(Wishlist w);

    // delete from database
    @Delete
    void deleteWishlist(Wishlist w);

    // delete table contents
    @Query("DELETE FROM wishlists")
    void deleteAllWishlists();

    @Update
    void updateWishlist(Wishlist w);

    @Query("Select * from wishlists")
    List<Wishlist> getallWishlists();

    @Query("SELECT * FROM wishlists " +
            "WHERE id = :id ")
    List<Wishlist> getWishlistByID(int id);

    @Query("SELECT * FROM wishlists " +
            "WHERE attraction_name = :attraction ")
    List<Wishlist> getWishlistsByAttraction(String attraction);

    @Query("SELECT * FROM wishlists " +
            "WHERE user_email = :email ")
    List<Wishlist> getWishlistsByUserEmail(String email);

    @Query("SELECT u.name, w.attraction_name FROM wishlists AS w " +
            "INNER JOIN users AS u ON u.email = w.user_email")
    List<WishlistByName> getWishlistsWithName();

}
