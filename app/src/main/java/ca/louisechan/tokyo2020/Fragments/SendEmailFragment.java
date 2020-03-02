package ca.louisechan.tokyo2020.Fragments;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.louisechan.tokyo2020.Activities.MainActivity;
import ca.louisechan.tokyo2020.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendEmailFragment extends Fragment {
    private static final String TAG = "SendEmailFragment";
    private static final int MAX_MESSAGE_CHARS = 1000;

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

    public SendEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_send_email, container, false);

        // Initialize message and character counter views
        txtCharCounter = (TextView) view.findViewById(R.id.textCharsLeft);
        editMessage = (EditText) view.findViewById(R.id.editEmailMessage);
        editMessage.addTextChangedListener(tw);

        Button sendButton = (Button) view.findViewById(R.id.buttonSendEmail);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSubject = (EditText) view.findViewById(R.id.editEmailSubject);
                EditText editMessage = (EditText) view.findViewById(R.id.editEmailMessage);
                EditText editRecipient = (EditText) view.findViewById(R.id.editEmailRecipient);
                String subject = editSubject.getText().toString();
                String message = editMessage.getText().toString();
                String recipient = editRecipient.getText().toString().trim();

                if(subject.isEmpty() || message.isEmpty() || recipient.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields before sending your message.", Toast.LENGTH_SHORT).show();
                } else {
                    // Send email to tourist info center
                    sendEmailMessage(recipient, subject, message);
                }

            }
        });

        return view;
    }

    public void sendEmailMessage(String recipient, String subject, String message) {
        String mailto = "mailto:" + recipient +
                "?subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(message);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        // Allow user to choose preferred email client
        try {
            startActivity(emailIntent);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No email client found.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder popupBox = new AlertDialog.Builder(getContext());

        // Show popup box confirming the email has been sent
        popupBox.setMessage("Message sent through your phone's email client.");
        popupBox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Return to home screen after user presses the OK button
                ((MainActivity) getActivity()).switchToHomeScreen();
            }
        });
        popupBox.show();
    }


}
