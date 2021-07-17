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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference dbRef;

    private EditText usnField, pwField;
    private ImageButton btnLogin, btnCancel;
    private TextView txtForgotPass, txtSignUp;

    private LoginFunctions functions;

    String PATH = "users";

    User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        dbRef = FirebaseDatabase.getInstance().getReference();

        functions = new LoginFunctions();

        usnField = findViewById(R.id.edtUsername);
        pwField = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        txtForgotPass = findViewById(R.id.txtForgot);
        txtSignUp = findViewById(R.id.txtSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newUser.setUsername(usnField.getText().toString());
                newUser.setPassword(pwField.getText().toString());
                String id = newUser.getUsername().replaceAll("\\s+","_");
                dbRef.child("users").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.getResult().child("username").getValue().toString().equals(newUser.getUsername()) && task.getResult().child("password").getValue().toString().equals(newUser.getPassword())){

                            //remove this *****
                            Toast.makeText(MainActivity.this, "correct input", Toast.LENGTH_SHORT).show();
                            //******

                            //Use this when view entry activity is made ***
//                            Intent gotoViewEntry = new Intent(getApplicationContext(), ViewEntry.class);
//                            startActivity(gotoViewEntry);
                            //***


                        }else{
                            Toast.makeText(MainActivity.this, "Incorrect credentials!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functions.Cancel(getApplicationContext());
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoChangePW = new Intent(getApplicationContext(), EnterUsername.class);
                startActivity(gotoChangePW);
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(gotoRegister);
            }
        });
    }
}