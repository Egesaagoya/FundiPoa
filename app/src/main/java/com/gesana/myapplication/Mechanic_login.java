package com.gesana.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mechanic_login extends AppCompatActivity {
    AppCompatButton loginbtn;
    TextView regBtn;
    TextInputLayout loginMail,loginPass;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://fundipoa-365512-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);
        loginbtn = findViewById(R.id.loginbtn);
        regBtn = findViewById(R.id.regBtn);
        loginMail = findViewById(R.id.loginMail);
        loginPass = findViewById(R.id.loginPass);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = loginMail.getEditText().getText().toString();
                String Passcode = loginPass.getEditText().getText().toString();

                if (Email.isEmpty() || Passcode.isEmpty()){
                    Toast.makeText(Mechanic_login.this, "Please Enter Email or password", Toast.LENGTH_SHORT).show();
                }else{


                    databaseReference.child("Mechanics").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(Email)){
                                final  String getPass = snapshot.child(Email).child("Password").getValue(String.class);

                                if (getPass.equals(Passcode)){
                                    Toast.makeText(Mechanic_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(Mechanic_login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });


        }
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Mechanic_reg.class);
                startActivity(intent);
                return;
            }
        });
    }
});
}
}