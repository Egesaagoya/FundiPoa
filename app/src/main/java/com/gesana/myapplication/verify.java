package com.gesana.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class verify extends AppCompatActivity {
    PinView pinMeView;
    TextView textMobile;
    AppCompatButton verifybtn;
    String verificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        pinMeView = findViewById(R.id.pinMeView);
        verifybtn = findViewById(R.id.verifybtn);
        textMobile = findViewById(R.id.textMobile);

        textMobile.setText(String.format(
                "+254-%s",getIntent().getStringExtra("mobile")
        ));
        verificationId = getIntent().getStringExtra("verificationId");
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (pinMeView.getText().toString().trim().isEmpty()){
                    Toast.makeText(verify.this, "Enter Valid Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code  = pinMeView.getText().toString();

                if (verificationId != null){
                    verifybtn.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    verifybtn.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(verify.this, "Code was Invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return;
            }
        });

    }
}