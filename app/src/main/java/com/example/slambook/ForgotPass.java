package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotPass extends AppCompatActivity {

    private EditText passwordField, conPasswordField;
    private ImageButton btnChangePass;
    private TextView txtSignIn;

    private DatabaseReference rbRef;

    String username;

    User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        rbRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");

        passwordField = findViewById(R.id.edtChangePW);
        conPasswordField = findViewById(R.id.edtConChangePW);
        btnChangePass = findViewById(R.id.btnChangePass);
        txtSignIn = findViewById(R.id.txtChangePass);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordField.getText().toString().equals(conPasswordField.getText().toString())){

                    rbRef.child("users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            newUser.setUsername(username);
                            newUser.setFullname(task.getResult().child("fullname").getValue().toString());
                            newUser.setPassword(passwordField.getText().toString());
                            newUser.setQuestion(task.getResult().child("question").getValue().toString());
                            newUser.setAnswer(task.getResult().child("answer").getValue().toString());
                            rbRef.child("users").child(username).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ForgotPass.this, "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForgotPass.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }else{
                    Toast.makeText(ForgotPass.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}