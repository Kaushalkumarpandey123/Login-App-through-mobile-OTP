package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class enter_otp_screen extends AppCompatActivity {

    EditText otp1,otp2,otp3,otp4,otp5,otp6;
    Button verify_otp;
    TextView resend_otp;
    String get_otp_backend;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp_screen);

        final ProgressBar progressBar = findViewById(R.id.progressbar_verify_otp);
        verify_otp = findViewById(R.id.verify_otp_btn);
        TextView mobilenumber = findViewById(R.id.textmobilenumber);

        otp1 = findViewById(R.id.inputotp1);
        otp2 = findViewById(R.id.inputotp2);
        otp3 = findViewById(R.id.inputotp3);
        otp4 = findViewById(R.id.inputotp4);
        otp5 = findViewById(R.id.inputotp5);
        otp6 = findViewById(R.id.inputotp6);

        String number = getIntent().getStringExtra("mobile");
        mobilenumber.setText("+91-"+number);
        String name = getIntent().getStringExtra("username");

        get_otp_backend=getIntent().getStringExtra("backend_otp");

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!otp1.getText().toString().trim().isEmpty() && !otp2.getText().toString().trim().isEmpty() && !otp3.getText().toString().trim().isEmpty() && !otp4.getText().toString().trim().isEmpty() && !otp5.getText().toString().trim().isEmpty() && !otp6.getText().toString().trim().isEmpty() ){

                    progressBar.setVisibility(view.VISIBLE);
                    verify_otp.setVisibility(view.INVISIBLE);

                    String entercode_otp = otp1.getText().toString()+otp2.getText().toString()+
                            otp3.getText().toString()+otp4.getText().toString()+
                            otp5.getText().toString()+otp6.getText().toString();

                    if(get_otp_backend!=null)
                    {

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                get_otp_backend,entercode_otp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(view.GONE);
                                        verify_otp.setVisibility(view.VISIBLE);


                                        if(task.isSuccessful()){

                                            Toast.makeText(enter_otp_screen.this, "otp verified succesfully", Toast.LENGTH_SHORT).show();

                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            reference = firebaseDatabase.getReference("Userdata");

                                            String username_s = getIntent().getStringExtra("username");
                                            String Address_s = getIntent().getStringExtra("address");
                                            String PhoneNumber_s = getIntent().getStringExtra("phonenumber");
                                            String Email_s = getIntent().getStringExtra("email");
                                            String Dob_s = getIntent().getStringExtra("dob");
                                            String password_s = getIntent().getStringExtra("password");

                                            Storing_data storing_data = new Storing_data(username_s,Address_s,PhoneNumber_s,Email_s,Dob_s,password_s);

                                            reference.child(username_s).setValue(storing_data);
                                            Log.d("kaushal", "onClick: "+username_s+Address_s+PhoneNumber_s+Email_s+Dob_s+password_s);
                                            Toast.makeText(getApplicationContext(),"Account Succeccfully Created",Toast.LENGTH_LONG).show();

                                            storing_data.setNewusername(username_s);

                                            Intent intent = new Intent(getApplicationContext(), dasbord.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Toast.makeText(enter_otp_screen.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                    }
                    else {
                        progressBar.setVisibility(view.GONE);
                        verify_otp.setVisibility(view.VISIBLE);
                        Toast.makeText(enter_otp_screen.this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressBar.setVisibility(view.GONE);
                    verify_otp.setVisibility(view.VISIBLE);
                    Toast.makeText(enter_otp_screen.this, "Enter all otp numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();

        resend_otp= findViewById(R.id.Resend_otp);
        final ProgressBar progressBar1 = findViewById(R.id.progressbar_resend_otp);

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar1.setVisibility(view.VISIBLE);
                resend_otp.setVisibility(view.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + number,
                        60, TimeUnit.SECONDS,
                        enter_otp_screen.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(enter_otp_screen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String new_otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                get_otp_backend = new_otp;
                                Toast.makeText(enter_otp_screen.this,"OTP Resend Successfully..", Toast.LENGTH_SHORT).show();
                                progressBar1.setVisibility(view.GONE);
                                resend_otp.setVisibility(view.VISIBLE);
                            }
                        });
            }
        });

    }

    private void numberotpmove(){
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( !charSequence.toString().trim().isEmpty()){
                    otp2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( !charSequence.toString().trim().isEmpty()){
                    otp3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( !charSequence.toString().trim().isEmpty()){
                    otp4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( !charSequence.toString().trim().isEmpty()){
                    otp5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( !charSequence.toString().trim().isEmpty()){
                    otp6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}