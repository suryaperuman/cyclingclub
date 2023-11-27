package com.example.eastcyclingclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClubActivityCompleteProfile extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText phoneNumber, mainContact, instagramUsername, twitterUsername, facebookLink;
    Button completeProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_activity_complete_profile);

        // Getting the current user's username
        String userUsername;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                userUsername = null;
            } else {
                userUsername = extras.getString("userUsernameKey");
            }
        } else {
            userUsername = (String) savedInstanceState.getSerializable("userUsernameKey");
        }

        // Initializing database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(userUsername);

        // Variables for each text field and the complete profile button
        phoneNumber = findViewById(R.id.phoneNumber);
        mainContact = findViewById(R.id.mainContact);
        instagramUsername = findViewById(R.id.instagramUsername);
        twitterUsername = findViewById(R.id.twitterUsername);
        facebookLink = findViewById(R.id.facebookLink);
        completeProfile = findViewById(R.id.completeProfile);

        completeProfile.setOnClickListener(view -> {
            // Grabbing information from user input
            String phoneNumberText = phoneNumber.getText().toString();
            String mainContactText = mainContact.getText().toString();
            String instagramUsernameText = instagramUsername.getText().toString();
            String twitterUsernameText = twitterUsername.getText().toString();
            String facebookLinkText = facebookLink.getText().toString();

            if (phoneNumberText.isEmpty() && (instagramUsernameText.isEmpty() && twitterUsernameText.isEmpty() && facebookLinkText.isEmpty())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClubActivityCompleteProfile.this);
                builder.setTitle("Try again!");
                builder.setMessage("Please enter a phone number and at least 1 social media contact");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else if (phoneNumberText.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClubActivityCompleteProfile.this);
                builder.setTitle("Try again!");
                builder.setMessage("Please enter a phone number");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else if (instagramUsernameText.isEmpty() && twitterUsernameText.isEmpty() && facebookLinkText.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClubActivityCompleteProfile.this);
                builder.setTitle("Try again!");
                builder.setMessage("Please enter at least 1 social media contact");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else {
                DatabaseReference specificUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUsername);

                specificUserReference.child("phoneNumber").setValue(phoneNumberText);
                specificUserReference.child("mainContact").setValue(mainContactText);
                specificUserReference.child("instagramUsername").setValue(instagramUsernameText);
                specificUserReference.child("twitterUsername").setValue(twitterUsernameText);
                specificUserReference.child("facebookLink").setValue(facebookLinkText);

                Toast.makeText(ClubActivityCompleteProfile.this, "Profile Completed!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ClubActivityEvents.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }
        });
    }
}
