package com.lintech.yougo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tripDetail extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference tripsDB = FirebaseDatabase.getInstance().getReference("trips").child(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) signout();
            }
        };
    }

    public void changeTrip(View view) {
        final EditText destFld = (EditText) findViewById(R.id.destFld);
        final EditText longFld = (EditText) findViewById(R.id.longFld);
        final EditText latFld = (EditText) findViewById(R.id.latFld);
        final EditText radFld = (EditText) findViewById(R.id.radFld);
        final EditText emailFld = (EditText) findViewById(R.id.emailFld);
        String destination = destFld.getText().toString();
        String longitude = longFld.getText().toString();
        String latitude = latFld.getText().toString();
        String rad = radFld.getText().toString();
        String email = emailFld.getText().toString();
        Trip newTrip = new Trip(destination, longitude, latitude, rad, email);
        tripsDB.setValue(newTrip);
        Intent intent = new Intent(this, TripList.class);
        startActivity(intent);
        finish();
    }

    public void signout() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

}