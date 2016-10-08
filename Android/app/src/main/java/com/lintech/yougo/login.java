package com.lintech.yougo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) loggedIn();
            }
        };
    }

    public void login(View view){
        EditText emailFld = (EditText)findViewById(R.id.emailFld);
        EditText passFld = (EditText)findViewById(R.id.passFld);
        TextView loginErrLbl = (TextView)findViewById(R.id.logErrLbl);
        String email = emailFld.getText().toString();
        String pw = passFld.getText().toString();
        if(email.equals("") || pw.equals("")){
            loginErrLbl.setText("*Please Provide an Email and Password");
        } else {
            mAuth.signInWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            TextView loginErrLbl = (TextView) findViewById(R.id.logErrLbl);
                            loginErrLbl.setText("");
                            if (!task.isSuccessful()) {
                                loginErrLbl.setText("*Invalid Email and Password");
                            }
                        }
                    });
        }
    }

    public void loggedIn(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void signup(View view){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();
        TextView loginErrLbl = (TextView)findViewById(R.id.logErrLbl);
        EditText emailFld = (EditText)findViewById(R.id.emailFld);
        EditText passFld = (EditText)findViewById(R.id.passFld);
        emailFld.setText("");
        passFld.setText("");
        loginErrLbl.setText("");
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
