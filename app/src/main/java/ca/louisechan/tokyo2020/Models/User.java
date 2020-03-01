package ca.louisechan.tokyo2020.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    private String name;
    private String password;
    @ColumnInfo(name = "is_admin")
    private Boolean isAdmin;

    public User(@NonNull String email, String name, String password, Boolean isAdmin) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @Ignore
    public User(@NonNull String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NonNull String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "[Name: " + getName() + ", Email: " + getEmail() +
                ", Type: " + (getAdmin() ? "Admin User":"Regular User") + "]";
    }
}
