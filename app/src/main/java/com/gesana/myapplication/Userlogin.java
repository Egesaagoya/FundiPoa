package com.gesana.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Userlogin extends AppCompatActivity {
    AppCompatButton getOtp;
    TextInputLayout number;
    FirebaseAuth mAuth;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        getOtp = findViewById(R.id.getOTP);
        number = findViewById(R.id.number);
        progress = findViewById(R.id.progress);
        mAuth = FirebaseAuth.getInstance();

        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(number.getEditText().getText().toString())){
                    Toast.makeText(Userlogin.this, "Enter A Valid Phone Number", Toast.LENGTH_SHORT).show();
                }else {

                    sendVerificationCode();
                }
                progress.setVisibility(view.VISIBLE);
                getOtp.setVisibility(view.INVISIBLE);

            }
        });

    }

    private void sendVerificationCode() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+254"+number.getEditText().getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
   private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            progress.setVisibility(View.GONE);
            getOtp.setVisibility(View.VISIBLE);

            final String code = credential.getSmsCode();

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            progress.setVisibility(View.GONE);
            getOtp.setVisibility(View.VISIBLE);

            Toast.makeText(Userlogin.this, "Verification Failed", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            progress.setVisibility(View.GONE);
            getOtp.setVisibility(View.VISIBLE);
            Intent intent = new Intent(Userlogin.this,verify.class);
            intent.putExtra("mobile",number.getEditText().getText().toString());
            intent.putExtra("verificationID",verificationId);
            startActivity(intent);



        }
    };

}