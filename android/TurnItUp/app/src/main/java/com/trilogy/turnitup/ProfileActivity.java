package com.trilogy.turnitup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;

    private TextView mUserID;
    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mUserBirthDate;
    private TextView mUserJoinDate;
    private TextView mUserHighscore;
    private EditText mEditBirthDate;
    private EditText mEditUserName;
    private Button mEditButton;
    private Button mSaveButton;
    private ImageView mProfilePicture;
    private ImageView mEditProfilePicture;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private DatabaseReference reference;
    StorageReference mStorage;
    private FirebaseAuth mAuth;
    User userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        mUserID = (TextView) findViewById(R.id.user_id);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUserEmail = (TextView) findViewById(R.id.user_email);
        mUserBirthDate = (TextView) findViewById(R.id.user_birth_date);
        mUserJoinDate = (TextView) findViewById(R.id.user_join_date);
        mUserHighscore = (TextView) findViewById(R.id.user_total_highscore);
        mEditBirthDate = (EditText) findViewById(R.id.edit_birth_date);
        mEditUserName = (EditText) findViewById(R.id.edit_user_name);
        mEditButton = (Button) findViewById(R.id.edit_profile);
        mSaveButton = (Button) findViewById(R.id.save_profile);
        mProfilePicture = (ImageView) findViewById(R.id.profile_picture);
        mEditProfilePicture = (ImageView) findViewById(R.id.edit_profile_picture);
        mEditBirthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mCurrentDate = Calendar.getInstance();
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, selectedyear);
                        calendar.set(Calendar.MONTH, selectedmonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String date = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
                        mEditBirthDate.setText(date);
                    }

                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }

        });
        retrieveDatabase();
    }

    private void retrieveDatabase() {
        reference = mDatabase.child("users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDB = dataSnapshot.getValue(User.class);
                updateProfile();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void signOut(View view) {
        stopService(MainActivity.svc);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("login").setValue("none");
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void updateProfile() {
        try {
            final File localFile = File.createTempFile("images", "jpg");
            mStorage.child("pp" + user.getUid() + ".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    mProfilePicture.setImageURI(Uri.fromFile(localFile));
                    mEditProfilePicture.setVisibility(View.GONE);
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(Uri.fromFile(localFile)).build();
                    user.updateProfile(profileUpdates);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        mUserID.setText(user.getUid());
        mUserEmail.setText(userDB.getEmail());
        mUserName.setText(userDB.getUsername());
        mUserBirthDate.setText(userDB.getBirthdate());
        mUserJoinDate.setText(userDB.getJoindate());
        int totalHighScore = 0;
        for (int highScore : userDB.getHighscore().values()) {
            totalHighScore += highScore;
        }
        mUserHighscore.setText(String.valueOf(totalHighScore));
    }

    public void editProfile(View view) {
        mEditBirthDate.setText(mUserBirthDate.getText().toString());
        mEditUserName.setText(mUserName.getText().toString());
        mEditButton.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.VISIBLE);
        mUserBirthDate.setVisibility(View.GONE);
        mEditBirthDate.setVisibility(View.VISIBLE);
        mUserName.setVisibility(View.GONE);
        mEditUserName.setVisibility(View.VISIBLE);
        mProfilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }

        });
    }

    public void saveProfile(View view) {
        mUserName.setText(mEditUserName.getText().toString());
        mUserBirthDate.setText(mEditBirthDate.getText().toString());
        userDB.setUsername(mEditUserName.getText().toString());
        userDB.setBirthdate(mEditBirthDate.getText().toString());
        mDatabase.child("users").child(user.getUid()).setValue(userDB);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mEditUserName.getText().toString()).build();
        user.updateProfile(profileUpdates);
        mUserBirthDate.setVisibility(View.VISIBLE);
        mEditBirthDate.setVisibility(View.GONE);
        mEditButton.setVisibility(View.VISIBLE);
        mSaveButton.setVisibility(View.GONE);
        mUserName.setVisibility(View.VISIBLE);
        mEditUserName.setVisibility(View.GONE);
        mProfilePicture.setOnClickListener(null);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mProfilePicture.setImageBitmap(bitmap);
                mEditProfilePicture.setVisibility(View.GONE);
                mStorage.child("pp" + user.getUid() + ".jpg").putFile(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
