package ca.louisechan.tokyo2020.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.louisechan.tokyo2020.Fragments.AttractionListFragment;
import ca.louisechan.tokyo2020.Fragments.HomeFragment;
import ca.louisechan.tokyo2020.Fragments.RatingsFragment;
import ca.louisechan.tokyo2020.Fragments.SendEmailFragment;
import ca.louisechan.tokyo2020.Fragments.SendSMSFragment;
import ca.louisechan.tokyo2020.Fragments.ViewAllSkedsFragment;
import ca.louisechan.tokyo2020.Fragments.ViewSkedByDayFragment;
import ca.louisechan.tokyo2020.Fragments.WishlistFragment;
import ca.louisechan.tokyo2020.Models.User;
import ca.louisechan.tokyo2020.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    // Unique permission code for call and sms intents
    public static final int CALL_PERMISSION_CODE = 100;
    public static final int SMS_PERMISSION_CODE = 101;
    private static final String TOKYO_TOURIST_CENTER_NUMBER = "+81-3-5321-3077";

    private String message;
    private String recipient;
    private static int year = 2020;
    private static int month = 06;          // month = July (Jan starts at 0 so 6 is July)
    private static int dayOfMonth = 22;

    private User currentUser;
    
    private boolean initialHomeScreenSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("loggedEmail");

        // Retrieve user info from database
        List<User> userList = LoginActivity.dbConnection.userDao().getUserByEmail(email);
        currentUser = userList.get(0);

        // Update nav drawer header with user info
        View headerView = navigationView.getHeaderView(0);
        TextView title = (TextView) headerView.findViewById(R.id.navhead_title);
        title.setText("Hello, " + currentUser.getName());
        TextView subtitle = (TextView) headerView.findViewById(R.id.navhead_subtitle);
        subtitle.setText(currentUser.getEmail());
        
        // Check if we need to load the home screen
        if(!initialHomeScreenSet) {
            Log.d(TAG, "onCreate: Home screen loaded for the first time!");
            // Indicate that home screen has been loaded at least once.
            initialHomeScreenSet = true;
            switchToHomeScreen();
        }
        else {
            Log.d(TAG, "onCreate: Home screen loading skipped during MainActivity oncreate call.");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Check if user is an admin
        if(!currentUser.getAdmin()) {
            // Hide admin UI access for non-admin users.
            MenuItem item = menu.findItem(R.id.action_admin);
            if (item != null){
                item.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Check if user has logged out
        if (id == R.id.action_logout) {
            Log.d(TAG, "onOptionsItemSelected: User " + currentUser.getEmail() + " has logged out.");
            // Switch to log-in activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_admin) {
            Log.d(TAG, "onOptionsItemSelected: Switching to admin UI activity.");

            // Switch to admin interface. Pass email address of logged user.
            Intent intent = new Intent(this, AdminActivity.class);
            intent.putExtra("loggedEmail", currentUser.getEmail());
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_contact_us) {
            Log.d(TAG, "onOptionsItemSelected: Contact us menu icon was clicked.");
            return true;
        }
        else if(id == R.id.action_phone) {
            Log.d(TAG, "onOptionsItemSelected: Contact us by phone was clicked.");
            AlertDialog.Builder popupBox = new AlertDialog.Builder(this);

            // Show popup box confirming the call
            popupBox.setTitle("Calling Tokyo Tourist Info Center...");
            popupBox.setMessage("Proceed with the call?\n(WARNING: Call charges may apply!)");
            popupBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "onClick: Making a call to Tokyo Tourist Information Center...");
                    // Check if call permissions are allow, if it is make the call.
                    // Otherwise, request call permission from user.
                    callPhoneButtonPressed();
                }
            });

            // Do nothing if no is clicked by user.
            popupBox.setNegativeButton("No", null);

            // Show the alert dialog box
            AlertDialog dialog = popupBox.show();
            // Center the message text (call show() prior to fetching text view)
            TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);

            return true;
        }
        else if(id == R.id.action_email) {
            Log.d(TAG, "onOptionsItemSelected: Contact us by email was clicked.");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_main_content, new SendEmailFragment());
            ft.commit();
            return true;
        }
        else if(id == R.id.action_sms) {
            Log.d(TAG, "onOptionsItemSelected: Contact us by SMS was clicked.");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_main_content, new SendSMSFragment());
            ft.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void callPhoneButtonPressed() {
        // Check if calls are allowed for this app.
        if(checkAppPermission(Manifest.permission.CALL_PHONE, CALL_PERMISSION_CODE)
                != PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "callPhoneButtonPressed: Call permission already granted.");

            // Call number
            callTouristInfoCenter();
        }
    }

    public int checkAppPermission(String permission, int permissionReqCode) {
        // Check if permission has already been granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    permissionReqCode);
            return PackageManager.PERMISSION_DENIED;
        }
        else {
            //Log.d(TAG, "Permission already granted.");
            return PackageManager.PERMISSION_GRANTED;
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Return to home screen
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            Log.d(TAG, "onNavigationItemSelected: Home menu item was clicked!");
            switchToHomeScreen();
        }
        else if (id == R.id.nav_list_attractions) {
            Log.d(TAG, "onNavigationItemSelected: List attractions menu item was clicked!");
            ft.replace(R.id.frame_main_content, new AttractionListFragment());

        }
        else if (id == R.id.nav_wishlist) {
            Log.d(TAG, "onNavigationItemSelected: List wishlist menu item was clicked!");
            ft.replace(R.id.frame_main_content, new WishlistFragment());

        }
        else if (id == R.id.nav_ratings) {
            Log.d(TAG, "onNavigationItemSelected: List ratings menu item was clicked!");
            ft.replace(R.id.frame_main_content, new RatingsFragment());

        }
        else if (id == R.id.nav_view_all_sked) {
            Log.d(TAG, "onNavigationItemSelected: View all sked menu item was clicked!");
            ft.replace(R.id.frame_main_content, new ViewAllSkedsFragment());

        }
        else if (id == R.id.nav_view_sked_by_day) {
            Log.d(TAG, "onNavigationItemSelected: View sked by day menu item was clicked!");
            ft.replace(R.id.frame_main_content, new ViewSkedByDayFragment());
        }
        else if (id == R.id.nav_set_reminder) {
            Log.d(TAG, "onNavigationItemSelected: Set reminder menu item was clicked!");
            ft.replace(R.id.frame_main_content, new HomeFragment());
        }
        else {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        ft.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Call Permission Granted", Toast.LENGTH_SHORT).show();

                // Call tourist information center number
                callTouristInfoCenter();
            }
            else {
                Toast.makeText(this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: SMS Permission granted");

                // Send SMS message to tourist info center
                sendSMSMessage(recipient, message);

            }
            else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void callTouristInfoCenter() {

        String formattedPhoneNumber = "tel:" + TOKYO_TOURIST_CENTER_NUMBER;

        Log.d(TAG, "Formatted phone number is: " + formattedPhoneNumber);

        // Make the call using the formatted phone number
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse(formattedPhoneNumber));

        // Check if dialer app is available (e.g. Phone app)
        if(i.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(this, "Calling " + TOKYO_TOURIST_CENTER_NUMBER + "...", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
        else {
            Toast.makeText(this, "ERROR: Can't find app to use for the phone call...", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ERROR: Cannot find app that matches ACTION_CALL intent");
        }
    }

    public void sendSMSMessage(String recipient, String message) {
        // Configure the SMSC (Short Message Service Center)
        String scAddress = null;

        // Create the intent
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;

        // Send the SMS using SMSManager (built in android class)
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(recipient, scAddress, message, sentIntent, deliveryIntent);

        AlertDialog.Builder popupBox = new AlertDialog.Builder(this);

        // Show popup box confirming the call
        popupBox.setMessage("Message sent to " + recipient + ".");
        popupBox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Return to home screen after user presses the OK button
                switchToHomeScreen();
            }
        });
        popupBox.show();
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public void setSMSData(String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
    }

    public void switchToHomeScreen() {
        // Return to home screen
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_main_content, new HomeFragment());
        ft.commit();
    }

    public void setDateFromPicker(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_main_content, new ViewSkedByDayFragment());
        ft.commit();
    }

    public int getSelectedYear() {
        return year;
    }
    public int getSelectedMonth() {
        return month;
    }
    public int getSelectedDayOfMonth() {
        return dayOfMonth;
    }
}
