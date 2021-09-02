package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class dasbord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasbord);

        Storing_data storing_data = new Storing_data();

        TextView fullname = (TextView) findViewById(R.id.fullname_dassbord);
        TextView Email_profile = (TextView) findViewById(R.id.email_profile_dassbord);

        TextView username = (TextView) findViewById(R.id.Name_dasboard);
        TextView email = (TextView) findViewById(R.id.Email_dasboard);
        TextView Phone_No = (TextView) findViewById(R.id.Phone_dasboard);
        TextView Dob = (TextView) findViewById(R.id.DOB_dasboard);
        TextView Address = (TextView) findViewById(R.id.Address_dasboard);

        String user = storing_data.getNewusername();
        fullname.setText(user);
        username.setText(user);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Userdata");
        Query check_username = databaseReference.orderByChild("username").equalTo(user);
        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Email_profile.setText(snapshot.child(user).child("email").getValue(String.class));
                email.setText(snapshot.child(user).child("email").getValue(String.class));
                Phone_No.setText(snapshot.child(user).child("phonenumber").getValue(String.class));
                Dob.setText(snapshot.child(user).child("dob").getValue(String.class));
                Address.setText(snapshot.child(user).child("address").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}