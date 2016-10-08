package com.lintech.yougo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newTrip extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference tripsDB = FirebaseDatabase.getInstance().getReference("trips");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) signout();
            }
        };
    }

    public void addTrip(View view){
        EditText longFld = (EditText)findViewById(R.id.longFld);
        EditText latFld = (EditText)findViewById(R.id.latFld);
        EditText radFld = (EditText)findViewById(R.id.radFld);
        String longitude = longFld.getText().toString();
        String latitude = latFld.getText().toString();
        String rad = radFld.getText().toString();
        Trip newTrip = new Trip(longitude, latitude, rad);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tripsDB.child(user.getUid()).push().setValue(newTrip);
        Intent intent = new Intent(this, TripList.class);
        startActivity(intent);
    }

    public void signout(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
