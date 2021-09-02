package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class create_account extends AppCompatActivity {

    Button createaccountbtn2;
    TextInputLayout username,Address,Phonenumber,Email,Dob,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createaccountbtn2=findViewById(R.id.send_otp_btn_account_create);
        username = findViewById(R.id.Username_Create_Account);
        Address = findViewById(R.id.Address_Create_Account);
        Phonenumber = findViewById(R.id.Phone_Create_Account);
        Email = findViewById(R.id.Email_Create_Account);
        Dob = findViewById(R.id.DOB_Create_Account);
        password = findViewById(R.id.Password_Create_Account);

        createaccountbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username_ = username.getEditText().getText().toString();
                String Address_ = Address.getEditText().getText().toString();
                String PhoneNumber_ = Phonenumber.getEditText().getText().toString();
                String Email_ = Email.getEditText().getText().toString();
                String Dob_ = Dob.getEditText().getText().toString();
                String password_ = password.getEditText().getText().toString();

                if(!username_.isEmpty())
                {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    if(!Address_.isEmpty())
                    {
                        Address.setError(null);
                        Address.setErrorEnabled(false);
                        if(!PhoneNumber_.isEmpty())
                        {
                            Phonenumber.setError(null);
                            Phonenumber.setErrorEnabled(false);
                            if(!Email_.isEmpty())
                            {
                                Email.setError(null);
                                Email.setErrorEnabled(false);
                                if(!Dob_.isEmpty())
                                {
                                    Dob.setError(null);
                                    Dob.setErrorEnabled(false);
                                    if(!password_.isEmpty())
                                    {
                                        password.setError(null);
                                        password.setErrorEnabled(false);

                                            String username_s = username.getEditText().getText().toString();
                                            String Address_s = Address.getEditText().getText().toString();
                                            String PhoneNumber_s = Phonenumber.getEditText().getText().toString();
                                            String Email_s = Email.getEditText().getText().toString();
                                            String Dob_s = Dob.getEditText().getText().toString();
                                            String password_s = password.getEditText().getText().toString();


                                        if (PhoneNumber_.length() == 10) {

                                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                    "+91" + Phonenumber.getEditText().getText().toString(),
                                                    60, TimeUnit.SECONDS,
                                                    create_account.this,
                                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                        @Override
                                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                                        }

                                                        @Override
                                                        public void onVerificationFailed(@NonNull FirebaseException e) {

                                                            Toast.makeText(create_account.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                                        }

                                                        @Override
                                                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                                            Intent intent = new Intent(getApplicationContext(),enter_otp_screen.class);
                                                            intent.putExtra("mobile", PhoneNumber_);
                                                            intent.putExtra("backend_otp",s);

                                                            intent.putExtra("username",username_s);
                                                            intent.putExtra("address",Address_s);
                                                            intent.putExtra("phonenumber",PhoneNumber_s);
                                                            intent.putExtra("email",Email_s);
                                                            intent.putExtra("dob",Dob_s);
                                                            intent.putExtra("password",password_s);

                                                            startActivity(intent);
                                                        }
                                                    });

                                        } else {
                                            Toast.makeText(create_account.this, "Enter correct mobile number", Toast.LENGTH_SHORT).show();
                                        }

                                    }else {
                                        password.setError("Please Enter the Password");
                                    }
                                }else {
                                    Dob.setError("Please Enter the DOB");
                                }
                            }else {
                                Email.setError("Please Enter the Email");
                            }
                        }else {
                            Phonenumber.setError("Please Enter the PhoneNumber");
                        }
                    }else {
                        Address.setError("Please Enter the Address");
                    }
                }else {
                    username.setError("Please Enter the username");
                }

            }
        });

    }
}