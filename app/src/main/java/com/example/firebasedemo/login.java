package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button createaccountbtn,loginbtn,forgot_login;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final ProgressBar progressBar = findViewById(R.id.progressbar_login);

       forgot_login = (Button) findViewById(R.id.forgot_login);
       loginbtn = (Button) findViewById(R.id.login_btn);
       createaccountbtn=(Button) findViewById(R.id.create_account_login_btn);
       username = findViewById(R.id.Username_Login);
       password = findViewById(R.id.Password_Login);

        createaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),create_account.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username_ = username.getEditText().getText().toString();
                String password_ = password.getEditText().getText().toString();

                if(!username_.isEmpty())
                {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    if(!password_.isEmpty())
                    {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        progressBar.setVisibility(v.VISIBLE);
                        loginbtn.setVisibility(v.INVISIBLE);

                      final String username_data = username.getEditText().getText().toString();
                        final String password_data = password.getEditText().getText().toString();

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("Userdata");
                        Query check_username = databaseReference.orderByChild("username").equalTo(username_data);
                        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    username.setError(null);
                                    username.setErrorEnabled(false);
                                    String password_check = snapshot.child(username_data).child("password").getValue(String.class);
                                    if(password_check.equals(password_data)){
                                        password.setError(null);
                                        password.setErrorEnabled(false);
                                        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();

                                        Storing_data storing_data = new Storing_data();
                                        storing_data.setNewusername(username_data);

                                        progressBar.setVisibility(v.GONE);
                                        loginbtn.setVisibility(v.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(),dasbord.class);
                                        startActivity(intent);
                                    }else {
                                        progressBar.setVisibility(v.GONE);
                                        loginbtn.setVisibility(v.VISIBLE);
                                        password.setError("Wrong Password");
                                    }
                                }else {
                                    progressBar.setVisibility(v.GONE);
                                    loginbtn.setVisibility(v.VISIBLE);
                                    username.setError("Invalid username");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else {
                       password.setError("Please Enter the Password");
                    }
                }else
                {
                    username.setError("Please Enter the username");
                }

            }
        });

        forgot_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),forgot_password.class);
                startActivity(intent);

            }
        });

    }
}