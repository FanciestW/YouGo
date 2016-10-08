package com.lintech.yougo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class TripList extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        ListView list = (ListView)findViewById(R.id.tripListView);
        list.
    }

    public void signout(View view){
        mAuth.getInstance().signOut();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void newTrip(View view){
        Intent intent = new Intent(this, newTrip.class);
        startActivity(intent);
    }
}
