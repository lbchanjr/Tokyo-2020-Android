package ca.louisechan.tokyo2020.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ca.louisechan.tokyo2020.Database.Tokyo2020Database;
import ca.louisechan.tokyo2020.Models.User;
import ca.louisechan.tokyo2020.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    public static Tokyo2020Database dbConnection = null;

    // Create a shared preferences variables
    SharedPreferences prefs;
    // Give local storage a unique name
    public static final String PREFERENCES_NAME = "LOGINPREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize and configure shared preferences variable
        prefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Log.d(TAG, "onCreate: Getting shared preferences... prefs = " + prefs);

        // Get checkbox status from local storage (SharedPreferences)
        boolean checkBoxSavedStatus = prefs.getBoolean("rememberMe", false);

        EditText editEmail = (EditText) findViewById(R.id.editLoginEmail);
        EditText editPass = (EditText) findViewById(R.id.editLoginPassword);

        // Initially clear the login edit boxes
        editEmail.setText("");
        editPass.setText("");

        // Check if there's a need to load saved login data.
        if (checkBoxSavedStatus) {
            // Remember me checkbox is ticked, load email and password into the edit boxes
            String savedEmail = prefs.getString("loginEmail", "");
            String savedPassword = prefs.getString("loginPassword", "");
            editEmail.setText(savedEmail);
            editPass.setText(savedPassword);
        }

        // Initialize database connection
        if (dbConnection == null) {
            Log.d(TAG, "onCreate: Initializing dbConnection.");
            dbConnection = Room.databaseBuilder(getApplicationContext(), Tokyo2020Database.class,
                    "users").addMigrations(MIGRATION_1_2, MIGRATION_2_3).allowMainThreadQueries().build();
        }
        else {
            Log.d(TAG, "onCreate: dbConnection already initialized.");
        }

        // Check if there's a need to load log-in information
        CheckBox chkRemember = (CheckBox) findViewById(R.id.checkRememberMe);
        // Update checkbox status with saved status.
        chkRemember.setChecked(checkBoxSavedStatus);
        
    }

    public void loginButtonClicked(View view) {
        EditText editEmailLogin = (EditText) findViewById(R.id.editLoginEmail);
        String userEmail = editEmailLogin.getText().toString().trim();
        
        // check if email is a valid one
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Log.d(TAG, "registerButtonClicked: Invalid email inputted.");
            Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show();
            //showAlertDialogMessage("Log-in error!", "Invalid email address. Try again.");
        } 
        else {
            // Email is valid, check if it exists in the database.
            List<User> userList = LoginActivity.dbConnection.userDao().getUserByEmail(userEmail);
            if(userList.size() != 0) {
                // User exists in database.
                Log.d(TAG, "loginButtonClicked: Email exists in database");
                
                // Check if password is a match.
                User u = userList.get(0);
                String pass = u.getPassword();
                EditText editPass = (EditText) findViewById(R.id.editLoginPassword);
                String savedPass = editPass.getText().toString();
                if(pass.equals(savedPass)) {
                    // Update saved preferences based on "Remember" checkbox status
                    updateLoginSharedPrefs();

                    // Switching to main activity
                    switchToMainActivity(userEmail);

//                    // Password is a match. Check if user is an admin user.
//                    if(u.getAdmin()) {
//                        // User is admin, prompt which screen to switch to.
//                        AlertDialog.Builder popupBox = new AlertDialog.Builder(LoginActivity.this);
//
//                        popupBox.setTitle("Admin user detected!");
//                        popupBox.setMessage("Enter admin user-interface?");
//
//                        popupBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Log.d(TAG, "onClick: Admin user: Switching to admin user interface");
////                                // Switching to main activity
////                                switchToMainActivity(userEmail);
////                                Toast t = Toast.makeText(getApplicationContext(), "Switching to admin interface!", Toast.LENGTH_SHORT);
////                                t.show();
//                            }
//                        });
//
//                        popupBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Log.d(TAG, "onClick: Admin user -> Switching to regular user interface");
////                                // Switching to main activity
////                                switchToMainActivity(userEmail);
////                                Toast t = Toast.makeText(getApplicationContext(), "Switching to regular user interface", Toast.LENGTH_SHORT);
////                                t.show();
//                            }
//                        });
//
//                        popupBox.show();
//
//                    }
//                    else {
////                        // Switching to main activity
////                        switchToMainActivity(userEmail);
//                        Log.d(TAG, "loginButtonClicked: Regular user -> Switching to User activity!");
//                        //Toast t = Toast.makeText(getApplicationContext(), "Switching to regular user interface", Toast.LENGTH_SHORT);
//                        //t.show();
//                    }
                }
                else {
                    Log.d(TAG, "loginButtonClicked: Invalid password.");
                    Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show();

                }
            }
            else {
                Log.d(TAG, "loginButtonClicked: Email can't be found on database.");
                Toast.makeText(this, "Email does not exist!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void switchToMainActivity(String email) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedEmail", email);
        startActivity(intent);
    }


    private void updateLoginSharedPrefs() {
        // Configure login shared preferences for editing
        SharedPreferences.Editor prefsEditor = prefs.edit();

        CheckBox chkBoxRemember = (CheckBox) findViewById(R.id.checkRememberMe);
        if (chkBoxRemember.isChecked()) {
            // Save email and password info
            EditText editEmail = (EditText) findViewById(R.id.editLoginEmail);
            EditText editPass = (EditText) findViewById(R.id.editLoginPassword);

            // Save data to shared preferences (key:value pair)
            prefsEditor.putString("loginEmail", editEmail.getText().toString().trim());
            prefsEditor.putString("loginPassword", editPass.getText().toString());
            prefsEditor.putBoolean("rememberMe", true);
        }
        else {
            // clear login saved preferences
            prefsEditor.putString("loginEmail", "");
            prefsEditor.putString("loginPassword", "");
            prefsEditor.putBoolean("rememberMe", false);

        }

        // Commit changes
        prefsEditor.apply();
    }

    public void showAlertDialogMessage(String title, String message) {
        AlertDialog.Builder popupBox = new AlertDialog.Builder(LoginActivity.this);

        popupBox.setTitle(title);
        popupBox.setMessage(message);
        popupBox.setNeutralButton("OK", null);
        popupBox.show();

    }

    public void signupButtonClicked(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `users_copy` (`email` TEXT NOT NULL, "
                    + "`name` TEXT, `password` TEXT, `is_admin` INTEGER, PRIMARY KEY(`email`))");

            database.execSQL("INSERT INTO users_copy (email, name, password, is_admin) " +
                    "SELECT email, name, password, isAdmin FROM " +
                    "(SELECT MIN(id) as id, email, name, password, isAdmin FROM users GROUP BY email)");

            database.execSQL("DROP TABLE users");

            database.execSQL("ALTER TABLE users_copy RENAME TO users");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `attractions` (`name` TEXT NOT NULL, `address` TEXT, " +
                    "`image_url` TEXT, `brief_desc` TEXT, `website` TEXT, `youtube_url` TEXT, `detailed_desc` TEXT, " +
                    "`visit_fee` REAL, PRIMARY KEY(`name`))");

            database.execSQL("CREATE TABLE IF NOT EXISTS `ratings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`user_email` TEXT, `attraction_name` TEXT, `rating` REAL NOT NULL, FOREIGN KEY(`user_email`) REFERENCES " +
                    "`users`(`email`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`attraction_name`) REFERENCES " +
                    "`attractions`(`name`) ON UPDATE NO ACTION ON DELETE CASCADE )");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_ratings_user_email` ON `ratings` (`user_email`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_ratings_attraction_name` ON `ratings` (`attraction_name`)");

            database.execSQL("CREATE TABLE IF NOT EXISTS `wishlists` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`user_email` TEXT, `attraction_name` TEXT, FOREIGN KEY(`user_email`) REFERENCES `users`(`email`) " +
                    "ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`attraction_name`) REFERENCES `attractions`(`name`) " +
                    "ON UPDATE NO ACTION ON DELETE CASCADE )");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_wishlists_user_email` ON `wishlists` (`user_email`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_wishlists_attraction_name` ON `wishlists` (`attraction_name`)");
        }
    };

/*
    public void deleteAllUsers2(View view) {
        dbConnection.userDao().deleteAllUsers();
    }
*/
}
