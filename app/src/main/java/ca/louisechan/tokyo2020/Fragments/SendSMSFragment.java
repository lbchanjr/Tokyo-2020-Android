package ca.louisechan.tokyo2020.Fragments;


import android.Manifest;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendSMSFragment extends Fragment {
    private static final String TAG = "SendSMSFragment";
    private static final int MAX_MESSAGE_CHARS = 160;

    private View view;
    private EditText editMessage;
    private TextView txtCharCounter;

    // Add text watcher to monitor number of keys that are typed inside the message box.
    private final TextWatcher tw = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Update characters left on the top-right of the message body
            int charsTyped = s.length();
            if(charsTyped != 1) {
                txtCharCounter.setText(Integer.toString(MAX_MESSAGE_CHARS-charsTyped) + " characters left");
            }
            else {
                txtCharCounter.setText(Integer.toString(MAX_MESSAGE_CHARS-charsTyped) + " character left");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public SendSMSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_send_sms, container, false);

        // Initialize message and character counter views
        txtCharCounter = (TextView) view.findViewById(R.id.textCharsLeftSMS);
        editMessage = (EditText) view.findViewById(R.id.editSMSMessage);
        editMessage.addTextChangedListener(tw);

        Button sendButton = (Button) view.findViewById(R.id.buttonSendSMS);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editMessage = (EditText) view.findViewById(R.id.editSMSMessage);
                EditText editRecipient = (EditText) view.findViewById(R.id.editSMSRecipient);
                String message = editMessage.getText().toString();
                String recipient = editRecipient.getText().toString().trim();

                // Check if fields are empty
                if(message.isEmpty() || recipient.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields before sending your message.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Check if SMS are allowed for this app.
                    if(((MainActivity)getActivity()).checkAppPermission(Manifest.permission.SEND_SMS, MainActivity.SMS_PERMISSION_CODE)
                            != PackageManager.PERMISSION_DENIED) {
                        Log.d(TAG, "onClick: SMS Permission already granted.");
                        // Send SMS message to tourist info center
                        ((MainActivity) getActivity()).sendSMSMessage(recipient, message);
                    }
                    else {
                        // Update Main activity variables with the message and recipient info.
                        ((MainActivity) getActivity()).setSMSData(message, recipient);
                    }
                }

            }
        });

        return view;
    }


}
