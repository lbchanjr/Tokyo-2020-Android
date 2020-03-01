package ca.louisechan.tokyo2020.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.louisechan.tokyo2020.Models.User;
import ca.louisechan.tokyo2020.R;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Get checkbox view object reference
        CheckBox chkAdmin = (CheckBox) findViewById(R.id.checkAdminUser);

        // Check if user database is empty
        if (chkIfUserDBIsEmpty()) {
            // User database is empty, this means that the first user that will be registered will
            // always be an admin.

            // Disable checkbox
            chkAdmin.setEnabled(false);
            // Add first user as admin but disable admin credentials prompt.
            chkAdmin.setChecked(true);
            setAdminCredVisibility(false);

            // Inform user that the first registered user will have admin privileges!
            showFirstUserAlert();

        }
        else {
            // At least one user is in the database, enable admin user checkbox
            chkAdmin.setEnabled(true);

            if (chkAdmin.isChecked()) {
                setAdminCredVisibility(true);
            } else {
                setAdminCredVisibility(false);
            }
        }

        chkAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show toast message informing user that admin credentials need to be inputted for admin signup.
                    Toast t = Toast.makeText(getApplicationContext(), "Enter admin credentials to allow\nadmin user sign-up.",
                            Toast.LENGTH_SHORT);
                    // For some reason, android will display toast messages "left-aligned" so change the text gravity so that
                    // the message will appear centered when displayed at the bottom of the screen.
                    TextView tv = (TextView) t.getView().findViewById(android.R.id.message);
                    tv.setGravity(Gravity.CENTER);
                    t.show();
                }

                // Configure admin credential availability depending on the checkbox status.
                setAdminCredVisibility(isChecked);
            }
        });

    }

    private void setAdminCredVisibility(boolean isVisible) {
        LinearLayout layAdmin = (LinearLayout) findViewById(R.id.layoutAdminCred);

        int childCount = layAdmin.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = layAdmin.getChildAt(i);
            v.setEnabled(isVisible);
            if (v instanceof TextView) {
                if(isVisible) {
                    ((TextView) v).setTextColor(Color.BLACK);
                }
                else {
                    ((TextView) v).setTextColor(Color.LTGRAY);
                }
            }
        }
    }

    private boolean chkIfUserDBIsEmpty() {
        List<User> usersList = LoginActivity.dbConnection.userDao().getallUsers();
        if (usersList.size() == 0) {
            return true;
        }
        else {
            Log.d(TAG, "chkIfUserDBIsEmpty: --- List of users in database ---");
            for(User u : usersList) {
                Log.d(TAG, "chkIfUserDBIsEmpty: " + u.toString());
            }
            return false;
        }
    }

    public void showAlertDialogMessage(String title, String message) {
        AlertDialog.Builder popupBox = new AlertDialog.Builder(SignupActivity.this);

        popupBox.setTitle(title);
        popupBox.setMessage(message);
        popupBox.setNeutralButton("OK", null);
        popupBox.show();


    }

    private void showFirstUserAlert() {

        // TODO: Add popup message as a string resource
        String title = "First user registration!";
        String message = "This user will be registered with admin privileges.";

        showAlertDialogMessage(title, message);
    }

    public void registerButtonClicked(View view) {
        Log.d(TAG, "registerButtonClicked: Register button was clicked!");

        boolean signUpSuccessful = false;

        EditText editUserEmail = (EditText) findViewById(R.id.editEmailSignup);
        String userEmail = editUserEmail.getText().toString().trim();
        // check if email is a valid one
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Log.d(TAG, "registerButtonClicked: Invalid email inputted.");
            //Toast.makeText(this, "Invalid email! Try again.", Toast.LENGTH_SHORT).show();
            showAlertDialogMessage("Registration error!", "Invalid email address. Try again.");
        }
        else {
            Log.d(TAG, "registerButtonClicked: Valid email address: " + userEmail);
            // Check if user passwords are a match
            if(checkUserPasswordsMatch((EditText) findViewById(R.id.editPasswordSignup),
                    (EditText) findViewById(R.id.editPasswordConfirmSignup))) {
                // Passwords are a match!

                // Check if new user is an admin
                CheckBox chkBoxAdmin = (CheckBox) findViewById(R.id.checkAdminUser);
                if(chkBoxAdmin.isChecked()) {
                    // User has admin status, check if user is allowed to be an admin.
                    // Note: This is done by checking to see if the checkbox is greyed out
                    // (i.e. first user registration) or if admin credentials are valid.
                    if(!chkBoxAdmin.isEnabled()) {
                        // Admin user registration is allowed.
                        Log.d(TAG, "registerButtonClicked: First used registration. Admin user registration is allowed!");
                        // Add admin user to database
                        signUpSuccessful = addUserToDatabase(true);
                    }
                    else {
                        if(checkAdminCredIsValid()) {
                            // Admin credentials are valid
                            Log.d(TAG, "registerButtonClicked: Admin creds are valid. Admin user registration is allowed!");
                            // Add admin user to database
                            signUpSuccessful = addUserToDatabase(true);
                        }
                        else {
                            showAlertDialogMessage("Registration error!", "Wrong admin credentials! User can't be registered as admin.");
                            //Toast.makeText(this, "Wrong admin credentials! User can't be registered as admin.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    // Add regular user to database
                    signUpSuccessful = addUserToDatabase(false);
                }
            }
            else {
                // Inform user that passwords do not match
                showAlertDialogMessage("Registration error!", "User passwords do not match. Try again.");
                //Toast.makeText(this, "User passwords do not match! Try again.", Toast.LENGTH_SHORT).show();
            }

        }

        // If user registration is successful, return to log-in screen
        if(signUpSuccessful) {
            Intent intent = new Intent(this, LoginActivity.class);
            // TODO Check if there's a need to preload user email and password when returning to login screen
            startActivity(intent);
        }

    }

    private boolean addUserToDatabase(boolean isAdmin) {
        boolean addIsSuccessful = true;

        String name = ((EditText) findViewById(R.id.editNameSignup)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.editEmailSignup)).getText().toString().trim();
        String pass = ((EditText) findViewById(R.id.editPasswordSignup)).getText().toString();

//        // Check if a user with the same email already exists in the database
//        List<User> userList = LoginActivity.dbConnection.userDao().getUserByEmail(email);
//        if(userList.size() == 0) {
//
//            // Create a user based on the entries
//            User u = new User(email, name, pass, isAdmin);
//            // Add user to user table.
//            long rowId = LoginActivity.dbConnection.userDao().addUser(u);
//
//            Log.d(TAG, "addUserToDatabase: User " + u.toString() + " with row_id#: " + rowId + " was added to database.");
//        }
//        else {
//            showAlertDialogMessage("Registration error!", "A user with the same email address already exists!");
//            Log.d(TAG, "addUserToDatabase: User was not added to database.");
//            addIsSuccessful = false;
//        }

        // Add user to user table.
        User u = new User(email, name, pass, isAdmin);
        long rowId = LoginActivity.dbConnection.userDao().addUser(u);
        if(rowId > 0) {
            // User was successfully added to table
            Log.d(TAG, "addUserToDatabase: User " + u.toString() + " with row_id#: " + rowId + " was added to database. (rowID = " + rowId + ")");
        }
        else {
            showAlertDialogMessage("Registration error!", "A user with the same email address already exists!");
            Log.d(TAG, "addUserToDatabase: User already exists and was not added to database. (rowID = " + rowId + ")");
            addIsSuccessful = false;
        }

        return addIsSuccessful;
    }


    private boolean checkAdminCredIsValid() {
        EditText editAdminEmail = (EditText) findViewById(R.id.editAdminEmail);
        EditText editAdminPassword = (EditText) findViewById(R.id.editAdminPassword);

        String adminEmail = editAdminEmail.getText().toString().trim();
        String adminPass = editAdminPassword.getText().toString();

        List<User> adminList = LoginActivity.dbConnection.userDao().getUserByEmail(adminEmail);
        if (adminList.size() != 1) {
            Log.d(TAG, "checkAdminCredIsValid: Admin email does not exist. Count = " + adminList.size());
            return false;
        }
        else {
            // Email was found in database, check if it is an admin user.
            if (adminList.get(0).getAdmin()) {
                // User is an admin, check if passwords are a match.
                String adminInDBPass = adminList.get(0).getPassword();
                if (adminPass.equals(adminInDBPass)) {
                    Log.d(TAG, "checkAdminCredIsValid: Admin credentials are valid!");
                    return true;
                }
                else {
                    // User is an admin but passwords do not match
                    Log.d(TAG, "checkAdminCredIsValid: Admin password is not a match. " + adminPass + " vs " + adminInDBPass);
                    return false;
                }
            }
            else {
                // user is not an admin
                Log.d(TAG, "checkAdminCredIsValid: User with email: " + adminEmail + " is not an admin!");
                return false;
            }
        }

    }

    private boolean checkUserPasswordsMatch(EditText pass1, EditText pass2) {
        // Check if passwords are a match
        String passStr1 = pass1.getText().toString();
        String passStr2 = pass2.getText().toString();

        return (passStr1.equals(passStr2));
    }

    public void cancelButtonClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
