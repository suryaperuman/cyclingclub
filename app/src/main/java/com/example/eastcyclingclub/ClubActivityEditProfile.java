package com.example.eastcyclingclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClubActivityEditProfile extends AppCompatActivity {
    EditText editName, editEmail, editUsername, editPassword, editPhoneNumber, editMainContact, editInstagramUsername, editTwitterUsername, editFacebookLink;
    Button saveButton, changePictureTextButton;
    ImageButton changePictureImageButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    String userUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_activity_edit_profile);

        // Getting the current user's username
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

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editMainContact = findViewById(R.id.editMainContact);
        editInstagramUsername = findViewById(R.id.editInstagramUsername);
        editTwitterUsername = findViewById(R.id.editTwitterUsername);
        editFacebookLink = findViewById(R.id.editFacebookLink);

        saveButton = findViewById(R.id.saveButton);

        // TODO: Implement club picture upload and display
        changePictureTextButton = findViewById(R.id.changePictureTextButton);
        changePictureImageButton = findViewById(R.id.changePictureImageButton);

        DatabaseReference specificUserReference1 = FirebaseDatabase.getInstance().getReference().child("users").child(userUsername);

        final String[] nameFromDatabase = new String[1];
        final String[] emailFromDatabase = new String[1];
        final String[] usernameFromDatabase = new String[1];
        final String[] passwordFromDatabase = new String[1];
        final String[] phoneNumberFromDatabase = new String[1];
        final String[] mainContactFromDatabase = new String[1];
        final String[] instagramUsernameFromDatabase = new String[1];
        final String[] twitterUsernameFromDatabase = new String[1];
        final String[] facebookLinkFromDatabase = new String[1];
        specificUserReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    nameFromDatabase[0] = dataSnapshot.child("name").getValue(String.class);
                    emailFromDatabase[0] = dataSnapshot.child("email").getValue(String.class);
                    usernameFromDatabase[0] = dataSnapshot.child("username").getValue(String.class);
                    passwordFromDatabase[0] = dataSnapshot.child("password").getValue(String.class);
                    phoneNumberFromDatabase[0] = dataSnapshot.child("phoneNumber").getValue(String.class);
                    mainContactFromDatabase[0] = dataSnapshot.child("mainContact").getValue(String.class);
                    instagramUsernameFromDatabase[0] = dataSnapshot.child("instagramUsername").getValue(String.class);
                    twitterUsernameFromDatabase[0] = dataSnapshot.child("twitterUsername").getValue(String.class);
                    facebookLinkFromDatabase[0] = dataSnapshot.child("facebookLink").getValue(String.class);

                    // Check for null before setting values in EditText fields
                    if (nameFromDatabase[0] != null) {
                        editName.setText(nameFromDatabase[0]);
                    }

                    if (emailFromDatabase[0] != null) {
                        editEmail.setText(emailFromDatabase[0]);
                    }

                    if (usernameFromDatabase[0] != null) {
                        editUsername.setText(usernameFromDatabase[0]);
                    }

                    if (passwordFromDatabase[0] != null) {
                        editPassword.setText(passwordFromDatabase[0]);
                    }

                    if (phoneNumberFromDatabase[0] != null) {
                        editPhoneNumber.setText(phoneNumberFromDatabase[0]);
                    }

                    if (mainContactFromDatabase[0] != null) {
                        editMainContact.setText(mainContactFromDatabase[0]);
                    }

                    if (instagramUsernameFromDatabase[0] != null) {
                        editInstagramUsername.setText(instagramUsernameFromDatabase[0]);
                    }

                    if (twitterUsernameFromDatabase[0] != null) {
                        editTwitterUsername.setText(twitterUsernameFromDatabase[0]);
                    }

                    if (facebookLinkFromDatabase[0] != null) {
                        editFacebookLink.setText(facebookLinkFromDatabase[0]);
                    }

                    Log.d("TAG", "Edit profile values retrieved");
                }
                else {
                    Log.d("TAG", "Edit profile values do not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "Error retrieving edit profile values", databaseError.toException());
            }
        });

        saveButton.setOnClickListener(view -> {
            String editNameText = editName.getText().toString();
            String editEmailText = editEmail.getText().toString();
            String editUsernameText = editUsername.getText().toString();
            String editPasswordText = editPassword.getText().toString();
            String editPhoneNumberText = editPhoneNumber.getText().toString();
            String editMainContactText = editMainContact.getText().toString();
            String editInstagramUsernameText = editInstagramUsername.getText().toString();
            String editTwitterUsernameText = editTwitterUsername.getText().toString();
            String editFacebookLinkText = editFacebookLink.getText().toString();

            if (editNameText.isEmpty() || editEmailText.isEmpty() || editUsernameText.isEmpty() || editPasswordText.isEmpty() || editPhoneNumberText.isEmpty() || (editInstagramUsernameText.isEmpty() && editTwitterUsernameText.isEmpty() && editFacebookLinkText.isEmpty())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClubActivityEditProfile.this);
                builder.setTitle("Try again!");
                StringBuilder message = new StringBuilder("Please make sure you have the following entered:");
                if (editNameText.isEmpty()) {
                    message.append("\n\t- A name");
                }
                if (editEmailText.isEmpty()) {
                    message.append("\n\t- An email");
                }
                if (editUsernameText.isEmpty()) {
                    message.append("\n\t- A username");
                }
                if (editPasswordText.isEmpty()) {
                    message.append("\n\t- A password");
                }
                if (editPhoneNumberText.isEmpty()) {
                    message.append("\n\t- A phone number");
                }
                if (editInstagramUsernameText.isEmpty() && editTwitterUsernameText.isEmpty() && editFacebookLinkText.isEmpty()) {
                    message.append("\n\t- At least 1 social media contact");
                }
                builder.setMessage(message);
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

                specificUserReference.child("name").setValue(editNameText);
                specificUserReference.child("email").setValue(editEmailText);
                specificUserReference.child("username").setValue(editUsernameText);
                specificUserReference.child("password").setValue(editPasswordText);
                specificUserReference.child("phoneNumber").setValue(editPhoneNumberText);
                specificUserReference.child("mainContact").setValue(editMainContactText);
                specificUserReference.child("instagramUsername").setValue(editInstagramUsernameText);
                specificUserReference.child("twitterUsername").setValue(editTwitterUsernameText);
                specificUserReference.child("facebookLink").setValue(editFacebookLinkText);

                Toast.makeText(ClubActivityEditProfile.this, "Profile Saved!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ClubActivityProfile.class);
                intent.putExtra("userUsernameKey", userUsername);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
            }

        });
    }
}
