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

public class otp_forgot_password extends AppCompatActivity {

    EditText otp_1, otp_2, otp_3, otp_4, otp_5, otp_6;
    Button verify_otp;
    TextView resend_otp;
    String get_otp_backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_forgot_password);

        final ProgressBar progressBar = findViewById(R.id.progressbar_verify_otp_forgot);
            verify_otp = findViewById(R.id.verify_otp_btn_forgot);
            TextView mobilenumber = findViewById(R.id.textmobilenumber_forgot);

            otp_1 = findViewById(R.id.inputotp_1);
            otp_2 = findViewById(R.id.inputotp_2);
            otp_3 = findViewById(R.id.inputotp_3);
            otp_4 = findViewById(R.id.inputotp_4);
            otp_5 = findViewById(R.id.inputotp_5);
            otp_6 = findViewById(R.id.inputotp_6);

            String number = getIntent().getStringExtra("mobile");
            mobilenumber.setText("+91-" + number);
            String name = getIntent().getStringExtra("username");

           get_otp_backend = getIntent().getStringExtra("backend_otp");

            verify_otp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!otp_1.getText().toString().trim().isEmpty() && !otp_2.getText().toString().trim().isEmpty() &&
                            !otp_3.getText().toString().trim().isEmpty() && !otp_4.getText().toString().trim().isEmpty() &&
                            !otp_5.getText().toString().trim().isEmpty() && !otp_6.getText().toString().trim().isEmpty()) {

                        progressBar.setVisibility(view.VISIBLE);
                        verify_otp.setVisibility(view.INVISIBLE);

                        String entercode_otp = otp_1.getText().toString() + otp_2.getText().toString() +
                                otp_3.getText().toString() + otp_4.getText().toString() +
                                otp_5.getText().toString() + otp_6.getText().toString();

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

                                                Toast.makeText(otp_forgot_password.this, "otp verified", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(getApplicationContext(), new_password.class);
                                                intent.putExtra("username",name);
                                                intent.putExtra("mobile",number);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Toast.makeText(otp_forgot_password.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        }
                        else {
                            progressBar.setVisibility(view.GONE);
                            verify_otp.setVisibility(view.VISIBLE);
                            Toast.makeText(otp_forgot_password.this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        progressBar.setVisibility(view.GONE);
                        verify_otp.setVisibility(view.VISIBLE);
                        Toast.makeText(otp_forgot_password.this, "Enter all otp numbers", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            numberotpmove();

            resend_otp = findViewById(R.id.Resend_otp_forgot);
            final ProgressBar progressBar1 = findViewById(R.id.progressbar_resend_otp_forgot);

            resend_otp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressBar1.setVisibility(view.VISIBLE);
                    resend_otp.setVisibility(view.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + number,
                            60, TimeUnit.SECONDS,
                            otp_forgot_password.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(otp_forgot_password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String new_otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    get_otp_backend = new_otp;
                                    Toast.makeText(otp_forgot_password.this, "OTP Resend Successfully..", Toast.LENGTH_SHORT).show();
                                    progressBar1.setVisibility(view.GONE);
                                    resend_otp.setVisibility(view.VISIBLE);
                                }
                            });
                }
            });

        }

    private void numberotpmove() {
        otp_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    otp_2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    otp_3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    otp_4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    otp_5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    otp_6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}