package ca.louisechan.tokyo2020.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "users")
public class User {
    @PrimaryKey (autoGenerate = true)
    private int id;
    //private int id = 0;             // always set to 0 in order for autoGenerate to work
    private String name;
    private String email;
    private String password;
    private Boolean isAdmin;

    public User(String name, String email, String password, Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
                "User type: " + (getAdmin() ? "Admin User":"Regular User") + "]";
    }
}
