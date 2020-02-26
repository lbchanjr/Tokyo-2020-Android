package ca.louisechan.tokyo2020.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
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
                if(isVisible == true) {
                    ((TextView) v).setTextColor(Color.BLACK);
                }
                else {
                    ((TextView) v).setTextColor(Color.LTGRAY);
                }
            }
        }
    }

    private boolean chkIfUserDBIsEmpty() {
        List<User> l = LoginActivity.dbConnection.userDao().getallUsers();
        if (l.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    private void showFirstUserAlert() {

        AlertDialog.Builder popupBox = new AlertDialog.Builder(SignupActivity.this);

        popupBox.setTitle("First user registration!");
        // TODO: Add popup message as a string resource
        popupBox.setMessage("This user will be registered with admin privileges.");
        popupBox.setNeutralButton("OK", null);
        popupBox.show();
    }

    public void registerButtonClicked(View view) {
        Log.d(TAG, "registerButtonClicked: Register button was clicked!");

        EditText editUserEmail = (EditText) findViewById(R.id.editEmailSignup);
        String userEmail = editUserEmail.getText().toString().trim();
        // check if email is a valid one
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(this, "Invalid email! Try again.", Toast.LENGTH_SHORT).show();
        }
        else {
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
                        // TODO: Add user to database
                    }
                    else {
                        if(checkAdminCredIsValid()) {
                            // Admin credentials are valid
                            Log.d(TAG, "registerButtonClicked: Admin creds are valid. Admin user registration is allowed!");
                            // TODO: Add user to database
                        }
                        else {
                            Toast.makeText(this, "Wrong admin credentials! User can't be registered as admin.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    // TODO: Regular user registration, check for duplicate emails.
                }
            }
            else {
                // Inform user that passwords do not match
                Toast.makeText(this, "User passwords do not match! Try again.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean checkAdminCredIsValid() {
        EditText editAdminEmail = (EditText) findViewById(R.id.editAdminEmail);
        EditText editAdminPassword = (EditText) findViewById(R.id.editAdminPassword);

        String adminEmail = editAdminEmail.getText().toString().trim();
        String adminPass = editAdminPassword.getText().toString();

        List<User> adminList = LoginActivity.dbConnection.userDao().getUserByEmail(adminEmail);
        if (adminList.size() != 1) {
            return false;
        }
        else {
            // Email was found in database, check if it is an admin user.
            if (adminList.get(0).getAdmin()) {
                // User is an admin, check if passwords are a match.
                if (adminPass.equals(adminList.get(0).getPassword())) {
                    return true;
                }
                else {
                    // User is an admin but passwords do not match
                    return false;
                }
            }
            else {
                // user is not an admin
                return false;
            }
        }

    }

    private boolean checkUserPasswordsMatch(EditText pass1, EditText pass2) {
        // Check if passwords are a match
        String passStr1 = pass1.getText().toString();
        String passStr2 = pass2.getText().toString();

        return (pass1.equals(pass2));
    }
}
