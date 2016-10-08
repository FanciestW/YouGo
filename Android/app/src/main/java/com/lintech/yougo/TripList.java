package com.lintech.yougo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TripList extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference tripsDB = FirebaseDatabase.getInstance().getReference("trips").child(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) signout(findViewById(R.id.signoutBtn));
            }
        };

        final TextView longTxt = (TextView)findViewById(R.id.longTxt);
        final TextView latTxt = (TextView)findViewById(R.id.latTxt);
        final TextView radTxt = (TextView)findViewById(R.id.radTxt);
        final TextView emailTxt = (TextView)findViewById(R.id.emailTxt);
        tripsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                longTxt.setText(trip.longitude);
                latTxt.setText(trip.latitude);
                radTxt.setText(trip.radius);
                emailTxt.setText(trip.email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error");
            }
        });
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
