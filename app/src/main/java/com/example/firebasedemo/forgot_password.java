package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class forgot_password extends AppCompatActivity {

    Button sendotp_forget_btn;
    TextInputLayout username_forget,number_forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        sendotp_forget_btn=(Button) findViewById(R.id.send_otp_forgot_btn);
        username_forget = findViewById(R.id.Username_Forgot);
        number_forget = findViewById(R.id.Number_forgot);
        final ProgressBar progressBar2 = findViewById(R.id.progressbar_send_otp_forgot);
        sendotp_forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username_ = username_forget.getEditText().getText().toString();
                String number_ = number_forget.getEditText().getText().toString();

                if(!username_.isEmpty())
                {
                    username_forget.setError(null);
                    username_forget.setErrorEnabled(false);
                    if(!number_.isEmpty())
                    {
                        number_forget.setError(null);
                        number_forget.setErrorEnabled(false);

                        progressBar2.setVisibility(v.VISIBLE);
                        sendotp_forget_btn.setVisibility(v.INVISIBLE);

                        final String username_data = username_forget.getEditText().getText().toString();
                        final String number_data = number_forget.getEditText().getText().toString();

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("Userdata");
                        Query check_username = databaseReference.orderByChild("username").equalTo(username_data);
                        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    username_forget.setError(null);
                                    username_forget.setErrorEnabled(false);
                                    String phonenumber_check = snapshot.child(username_data).child("phonenumber").getValue(String.class);
                                    if(phonenumber_check.equals(number_data)){
                                        number_forget.setError(null);
                                        number_forget.setErrorEnabled(false);

                                        if (number_data.length() == 10) {

                                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                "+91" + number_forget.getEditText().getText().toString(),
                                                60, TimeUnit.SECONDS,
                                                forgot_password.this,
                                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                    @Override
                                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                                    }

                                                    @Override
                                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                                        Toast.makeText(forgot_password.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                                    }

                                                    @Override
                                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                                        progressBar2.setVisibility(v.GONE);
                                                        sendotp_forget_btn.setVisibility(v.VISIBLE);
                                                        Toast.makeText(getApplicationContext(), "OTP send successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(),otp_forgot_password.class);
                                                        intent.putExtra("mobile", number_data);
                                                        intent.putExtra("username",username_data);
                                                        intent.putExtra("backend_otp",s);
                                                        startActivity(intent);
                                                    }
                                                });

                                    } else {
                                        progressBar2.setVisibility(v.GONE);
                                        sendotp_forget_btn.setVisibility(v.VISIBLE);
                                        Toast.makeText(forgot_password.this, "Enter correct mobile number", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else {
                                        progressBar2.setVisibility(v.GONE);
                                        sendotp_forget_btn.setVisibility(v.VISIBLE);
                                        number_forget.setError("mobilenumber not find");
                                    }

                                }else {
                                    progressBar2.setVisibility(v.GONE);
                                    sendotp_forget_btn.setVisibility(v.VISIBLE);
                                    username_forget.setError("Invalid username");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else {
                        number_forget.setError("Please Enter the mobilenumber");
                    }
                }else
                {
                    username_forget.setError("Please Enter the username");
                }

            }
        });
    }
}