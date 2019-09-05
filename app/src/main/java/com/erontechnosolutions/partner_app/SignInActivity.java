package com.erontechnosolutions.partner_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;

import Model.Vehicle_User;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG ="SignInActivity" ;
    EditText email, password;
    Button loginbtn;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email_edt);
        password = findViewById(R.id.pass_edt);
        loginbtn = findViewById(R.id.signin_btn);
        mAuth = FirebaseAuth.getInstance();



      /*  if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MapsActivity.class));
            finish();
        }
*/
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailstring, pass;
                emailstring = email.getText().toString();
                pass = password.getText().toString();



                if (TextUtils.isEmpty(emailstring)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailstring, pass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {


                            // there was an error
                            if (password.length() < 6) {
                                password.setError("Password should not be less than 6 characters");
                            } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }else {

                          userID=mAuth.getCurrentUser().getUid();

                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("vehicle_users");

                            Query query = reference.child(userID);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                   // dataSnapshot.getChildren();
                                    final Vehicle_User vehicle_user=dataSnapshot.getValue(Vehicle_User.class);


                                    String stat= vehicle_user != null ? vehicle_user.getStatus() : null;

                                    if(stat.equals("COMPLETED")){


                                        startActivity(new Intent(SignInActivity.this, MapsActivity.class));
                                        finish();

                                    }else {
                                        Toast.makeText(SignInActivity.this, "Your Account is on verification!!!", Toast.LENGTH_SHORT).show();


                                    }

                                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if(!task.isSuccessful()){
                                                Log.w(TAG, "onComplete: get Instance failed",task.getException() );
                                                return;
                                            }

                                            String token=task.getResult().getToken();
                                            HashMap<String, Object> result = new HashMap<>();
                                            result.put("token", token);
                                            FirebaseDatabase.getInstance().getReference().child("vehicle_users").child(vehicle_user.getId()).updateChildren(result);


                                           /* DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("vehicle_users");
                                            reference1.child("token").setValue(token);*/

                                            String msg=getString(R.string.fcm_token, token);
                                            Log.d(TAG, "onComplete: "+msg);
                                            Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                           /* DatabaseReference reference=FirebaseDatabase.getInstance().getReference("vehicle_users");
                            userID=mAuth.getUid();
                            //String status=
                            reference.child(userID).orderByChild("status").equalTo("COMPLETED").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    startActivity(new Intent(SignInActivity.this, MapsActivity.class));
                                    finish();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                }
                            });*/


                        }

                        }
                });





              /*  final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("vehicle_users").orderByChild("phone_number").equalTo(emailstring).addListenerForSingleValueEvent(new ValueEventListener() {
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
    }*/
            }
        });
    }
}
