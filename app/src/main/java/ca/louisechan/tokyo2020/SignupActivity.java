package ca.louisechan.tokyo2020;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        CheckBox chkAdmin = (CheckBox) findViewById(R.id.checkAdminUser);
        if (chkAdmin.isChecked()) {
            setAdminCredVisibility(true);
        }
        else {
            setAdminCredVisibility(false);
        }

        chkAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

}
