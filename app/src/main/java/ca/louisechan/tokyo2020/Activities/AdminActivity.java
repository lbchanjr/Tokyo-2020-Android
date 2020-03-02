package ca.louisechan.tokyo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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

        }
    }
}
