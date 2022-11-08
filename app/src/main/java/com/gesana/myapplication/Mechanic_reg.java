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

public class Mechanic_reg extends AppCompatActivity {
    TextInputLayout name,mail,telephone,passcode,pass2;
    AppCompatButton btnSign;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://fundipoa-365512-default-rtdb.firebaseio.com/");
    TextView logBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);

        name  = findViewById(R.id.name);
        mail  = findViewById(R.id.mail);
        telephone  = findViewById(R.id.telephone);
        passcode  = findViewById(R.id.passcode);
        pass2  = findViewById(R.id.pass2);
        btnSign  = findViewById(R.id.btnSign);
        logBt = findViewById(R.id.logBt);

        logBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Mechanic_login.class);
                startActivity(intent);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = name.getEditText().getText().toString().trim();
                String email = mail.getEditText().getText().toString().trim();
                String phone = telephone.getEditText().getText().toString().trim();
                String password = passcode.getEditText().getText().toString().trim();
                String confirmPassword = pass2.getEditText().getText().toString().trim();
                
                if(fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() ||confirmPassword.isEmpty()){
                    Toast.makeText(Mechanic_reg.this, "Please Fill all Fields", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirmPassword)){
                    Toast.makeText(Mechanic_reg.this, "Passwords are not Matching", Toast.LENGTH_SHORT).show();

                }else {
                    databaseReference.child("Mechanics").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phone)){
                                Toast.makeText(Mechanic_reg.this, "Phone Number is  already registered", Toast.LENGTH_SHORT).show();
                            }else{


                                databaseReference.child("Mechanics").child(phone).child("name").setValue(fullName);
                                databaseReference.child("Mechanics").child(phone).child("mail").setValue(email);
                                databaseReference.child("Mechanics").child(phone).child("passcode").setValue(password);

                                Toast.makeText(Mechanic_reg.this, "Mechanic registered Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Userlogin.class));
                                finish();
                                return;

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }




            }
        });

    }
}