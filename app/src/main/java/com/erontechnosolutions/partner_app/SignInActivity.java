package com.erontechnosolutions.partner_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    EditText phoneno, password;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phoneno = findViewById(R.id.mobileno_edt);
        password = findViewById(R.id.pass_edt);
        loginbtn = findViewById(R.id.signin_btn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                final String phonenum, pass;
                phonenum = phoneno.getText().toString();
                pass = password.getText().toString();
                mDatabase.child("vehicle_users").orderByChild("phone_number").equalTo(phonenum).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mDatabase.child("vehicle_users").orderByChild("password").equalTo(pass).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    mDatabase.child("vehilce_users").orderByChild("status").equalTo("COMPLETED").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            startActivity(new Intent(SignInActivity.this,MapsActivity.class));
                                            Toast.makeText(SignInActivity.this, "Success Login", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(SignInActivity.this, " Not verified Yet", Toast.LENGTH_SHORT).show();


                                        }
                                    });


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(SignInActivity.this, " Login Failed", Toast.LENGTH_SHORT).show();


                                }
                                //Username exists
                            });

                            //Username does not exist

                        }




                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
            }
        });
    }
}
