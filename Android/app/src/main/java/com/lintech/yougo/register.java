package com.lintech.yougo;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference("users");
    private DatabaseReference tripsDB = FirebaseDatabase.getInstance().getReference("trips");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) login();
            }
        };
    }

    public void signUp(View view){
        final TextView regErrLbl = (TextView)findViewById(R.id.regErrLbl);
        EditText regName = (EditText)findViewById(R.id.nameFld);
        EditText regEmail = (EditText)findViewById(R.id.emailFld);
        EditText regPass = (EditText)findViewById(R.id.passFld);
        final String name = regName.getText().toString();
        final String email = regEmail.getText().toString();
        final String password = regPass.getText().toString();
        if(name.equals("")||email.equals("")||password.equals("")){
            regErrLbl.setText("*Please Fill Out All Fields");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                regErrLbl.setText("Error Signing Up");
                            } else {
                                User user = new User(name, email, task.getResult().getUser().getUid());
                                Trip trip = new Trip("0", "0", "0", "");
                                newUser(user, trip);
                            }
                        }
                    });
        }
    }

    public void newUser(User newUser, Trip newTrip){
        usersDB.child(newUser.UID).setValue(newUser);
        tripsDB.child(newUser.UID).setValue(newTrip);
        login();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        EditText regName = (EditText)findViewById(R.id.nameFld);
        EditText regEmail = (EditText)findViewById(R.id.emailFld);
        EditText regPass = (EditText)findViewById(R.id.passFld);
        regName.setText("");
        regEmail.setText("");
        regPass.setText("");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void login(){
        Intent intent = new Intent(this, TripList.class);
        startActivity(intent);
    }

    public void gotoLogin(View view){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
