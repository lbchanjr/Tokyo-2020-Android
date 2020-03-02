package ca.louisechan.tokyo2020.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import ca.louisechan.tokyo2020.Models.Attraction;
import ca.louisechan.tokyo2020.R;

public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "AdminActivity";

    private String loggedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Retrieved save user email
        Intent intent = getIntent();
        loggedEmail = intent.getStringExtra("loggedEmail");

        // tell android which toolbar is the primary toolbar of the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
    }

    // Tell toolbar about menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // tell which layout file will be used for the menu
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuitem = item.getItemId();

        AlertDialog.Builder popupBox;

        switch (selectedMenuitem) {
            case R.id.admin_mi_logout:
                Log.d(TAG, "onOptionsItemSelected: Exitting to log-in screen");
                // Exit to login screen
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;

            case R.id.admin_mi_switchUI:
                Log.d(TAG, "onOptionsItemSelected: Switching to default UI display");

                // Switch main activity.
                Intent intentMain = new Intent(this, MainActivity.class);
                intentMain.putExtra("loggedEmail", loggedEmail);
                startActivity(intentMain);
                return true;

//            case R.id.admin_mi_delete_users:
//                Log.d(TAG, "onOptionsItemSelected: Deleting all users in database");
//
//                //popupBox = new AlertDialog.Builder(getApplicationContext());
//                popupBox = new AlertDialog.Builder(new ContextThemeWrapper(getBaseContext(), R.style.AppTheme2));
//                popupBox.setTitle("Deleting 'users' table...");
//                popupBox.setMessage("Are you sure about this?");
//                popupBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Confirm deletion of all users
//                        LoginActivity.dbConnection.userDao().deleteAllUsers();
//                        Log.d(TAG, "onClick: users table was deleted");
//                    }
//                });
//
//                // Do nothing if NO is clicked
//                popupBox.setNegativeButton("No", null);
//                popupBox.show();
//                return true;
//
//
//            case R.id.admin_mi_delete_attractions:
//                Log.d(TAG, "onOptionsItemSelected: Deleting all attractions in database");
//
//                //popupBox = new AlertDialog.Builder(getApplicationContext());
//                popupBox = new AlertDialog.Builder(new ContextThemeWrapper(getBaseContext(), R.style.AppTheme2));
//                popupBox.setTitle("Deleting 'attractions' table...");
//                popupBox.setMessage("Are you sure about this?");
//                popupBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Confirm deletion of attractions table
//                        LoginActivity.dbConnection.attractionDao().deleteAllAttractions();
//                        Log.d(TAG, "onClick: attractions table was deleted");
//                    }
//                });
//
//                // Do nothing if NO is clicked
//                popupBox.setNegativeButton("No", null);
//                popupBox.show();
//                return true;
//
//            case R.id.admin_mi_delete_wishlists:
//                Log.d(TAG, "onOptionsItemSelected: Deleting all wishlists in database");
//
//                //popupBox = new AlertDialog.Builder(getApplicationContext());
//                popupBox = new AlertDialog.Builder(new ContextThemeWrapper(getBaseContext(), R.style.AppTheme2));
//                popupBox.setTitle("Deleting 'wishlists' table...");
//                popupBox.setMessage("Are you sure about this?");
//                popupBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Confirm deletion of all wishlists
//                        LoginActivity.dbConnection.wishlistDao().deleteAllWishlists();
//                        Log.d(TAG, "onClick: wishlists table was deleted");
//                    }
//                });
//
//                // Do nothing if NO is clicked
//                popupBox.setNegativeButton("No", null);
//                popupBox.show();
//                return true;
//
//            case R.id.admin_mi_delete_ratings:
//                Log.d(TAG, "onOptionsItemSelected: Deleting all ratings in database");
//
//                //popupBox = new AlertDialog.Builder(getApplicationContext());
//                popupBox = new AlertDialog.Builder(new ContextThemeWrapper(getBaseContext(), R.style.AppTheme2));
//
//                popupBox.setTitle("Deleting 'ratings' table...");
//                popupBox.setMessage("Are you sure about this?");
//                popupBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Confirm deletion of all ratings
//                        LoginActivity.dbConnection.ratingDao().deleteAllRatings();
//                        Log.d(TAG, "onClick: ratings table was deleted");
//                    }
//                });
//
//                // Do nothing if NO is clicked
//                popupBox.setNegativeButton("No", null);
//                popupBox.show();
//                return true;

        }

        // default android code
        return super.onOptionsItemSelected(item);
    }

    public void clrAttrFieldsButtonClicked(View view) {
        LinearLayout adminFormFields = (LinearLayout) findViewById(R.id.linearAdminFields);

        for(int i = 0; i < adminFormFields.getChildCount(); i++) {
            // Check if view is an edittext view
            View v = adminFormFields.getChildAt(i);
            if( v instanceof EditText) {
                // Clear text attribute of all edittext inputs
                ((EditText) v).setText("");
            }
        }

    }

    public void addAttrButtonClicked(View view) {
        EditText editName = (EditText) findViewById(R.id.editAttName);
        EditText editAddr = (EditText) findViewById(R.id.editAttAddress);
        EditText editBriefDesc = (EditText) findViewById(R.id.editAttBriefDesc);

        if(editName.getText().toString().isEmpty()) {
            editName.setError("Attraction name cannot be empty.");
        }
        else if(editAddr.getText().toString().isEmpty()) {
            editAddr.setError("Attraction address cannot be empty.");
        }
        else if(editBriefDesc.getText().toString().isEmpty()) {
            editBriefDesc.setError("A short description of the attraction is required.");
        }
        else {
            // Add attraction record to attraction database
            Log.d(TAG, "addAttrButtonClicked: Adding attraction to database.");
            Attraction a = new Attraction(editName.getText().toString().trim());
            a.setAddress(editAddr.getText().toString().trim());
            String briefDesc = editBriefDesc.getText().toString().trim();
            a.setBriefDesc(briefDesc);

            // Check if detailed description is empty
            EditText edtDetailDesc = (EditText) findViewById(R.id.editAttDetailedDesc);
            if(edtDetailDesc.getText().toString().isEmpty()) {
                // Detailed desc is empty.
                // Copy brief description to detailed description.
                edtDetailDesc.setText(briefDesc);
            }
            else {
                a.setDetailedDesc(edtDetailDesc.getText().toString());
            }

            EditText editImgLink = (EditText) findViewById(R.id.editAttImageURL);
            EditText editVideoLink = (EditText) findViewById(R.id.editAttVideoURL);
            EditText editVisitFee = (EditText) findViewById(R.id.editAttVisitFee);

            double fee = Double.parseDouble(editVisitFee.getText().toString());
            a.setVisitFee(fee);
            a.setImageUrl(editImgLink.getText().toString().trim());
            a.setYoutubeUrl(editVideoLink.getText().toString().trim());

            // Add record to attractions table
            long rowId = LoginActivity.dbConnection.attractionDao().addAttraction(a);
            if(rowId > 0) {
                // User was successfully added to table
                Toast.makeText(this, "Attraction was successfully added into database.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "addAttrButtonClicked: Attraction was added to database. (rowID = " + rowId + ")");

                // Clear fields after adding record to database
                clrAttrFieldsButtonClicked(null);
            }
            else {
                AlertDialog.Builder popupBox = new AlertDialog.Builder(AdminActivity.this);

                popupBox.setTitle("Duplicate entry");
                popupBox.setMessage("An attraction with a similar name already exists in the database");
                popupBox.setNeutralButton("OK", null);
                popupBox.show();
                Log.d(TAG, "addAttrButtonClicked: Attraction name already exists and was not added to database. (rowID = " + rowId + ")");
            }
        }
    }
}
