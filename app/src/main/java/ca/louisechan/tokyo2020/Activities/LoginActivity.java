package ca.louisechan.tokyo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ca.louisechan.tokyo2020.Database.Tokyo2020Database;
import ca.louisechan.tokyo2020.R;

public class LoginActivity extends AppCompatActivity {

    public static Tokyo2020Database dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database connection
        dbConnection = Room.databaseBuilder(getApplicationContext(), Tokyo2020Database.class,
                "users").allowMainThreadQueries().build();
    }

    public void loginButtonClicked(View view) {
    }

    public void signupButtonClicked(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

}
