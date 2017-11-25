package com.trilogy.turnitup;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar mProgressBar;
    private EditText mUsernameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String mNotificationToken;
    private NotificationManager mNotifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_bar);
        mUsernameField = (EditText) findViewById(R.id.field_username);
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mConfirmPasswordField = (EditText) findViewById(R.id.field_confirm_password);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = mAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(mUsernameField.getText().toString()).build();
                    user.updateProfile(profileUpdates);
                    mNotificationToken = FirebaseInstanceId.getInstance().getToken();
                    addUsertoDatabase();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

        });
    }

    private void addUsertoDatabase() {
        HashMap<String, Integer> score = new HashMap<String, Integer>();
        score.put("Shake It Off", 0);
        score.put("Let It Go", 0);
        score.put("Timber", 0);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        User userDB = new User(mUsernameField.getText().toString(), mEmailField.getText().toString(), date, "", score, mNotificationToken);
        mDatabase.child("users").child(user.getUid()).setValue(userDB);

    }

    private boolean validateForm() {
        boolean valid = true;
        String username = mUsernameField.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsernameField.setError("Required");
            valid = false;
        } else {
            mUsernameField.setError(null);
        }
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required");
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        String confirmPassword = mConfirmPasswordField.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordField.setError("Required");
            valid = false;
        } else if (!password.equals(confirmPassword)){
            mConfirmPasswordField.setError("Password don't match");
            valid = false;
        } else {
            mConfirmPasswordField.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View view) {
        //view = RegisterActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }

}
