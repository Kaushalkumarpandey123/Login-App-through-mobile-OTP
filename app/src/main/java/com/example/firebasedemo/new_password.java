package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class new_password extends AppCompatActivity {
    private Button update_password_btn;
    private TextInputLayout new_password,confirm_password;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

       reference = FirebaseDatabase.getInstance().getReference("Userdata");

       final String number = getIntent().getStringExtra("mobile");
      final String name = getIntent().getStringExtra("username");

        update_password_btn=(Button) findViewById(R.id.update_password_btn);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.Confirm_password);

        update_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword_ = new_password.getEditText().getText().toString();
                String confirmpassword_ = confirm_password.getEditText().getText().toString();
                if(!newPassword_.isEmpty())
                {
                    new_password.setError(null);
                    new_password.setErrorEnabled(false);
                    if(!confirmpassword_.isEmpty())
                    {
                        confirm_password.setError(null);
                        confirm_password.setErrorEnabled(false);
                        final String newpassword_data = new_password.getEditText().getText().toString();
                        final String confirmpassword_data = confirm_password.getEditText().getText().toString();
                        if(newpassword_data.equals(confirmpassword_data)){
                            confirm_password.setError(null);
                            confirm_password.setErrorEnabled(false);
                            String username = getIntent().getStringExtra("username");
                            String password = new_password.getEditText().getText().toString();
                                 HashMap hashMap = new HashMap();
                                 hashMap.put("password",password);
                                reference.child(username).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                   @Override
                                    public void onSuccess(Object o) {

                                    Toast.makeText(getApplicationContext(), "Password update successfully..Login to continue.", Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(getApplicationContext(),login.class);
                                         startActivity(intent);
                                     }
                        });

                       }else {
                            confirm_password.setError("Passwords didn't match");
                        }
                    }else {
                        confirm_password.setError("Please Enter the Password");
                    }
                }else
                {
                    new_password.setError("Please Enter the username");
                }
            }
        });
    }
}