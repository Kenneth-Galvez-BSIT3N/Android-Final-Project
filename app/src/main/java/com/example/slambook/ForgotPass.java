package com.example.slambook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ForgotPass extends AppCompatActivity {

    private EditText passwordField, conPasswordField;
    private ImageButton btnChangePass;
    private TextView txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        passwordField = findViewById(R.id.edtChangePW);
        conPasswordField = findViewById(R.id.edtConChangePW);
        btnChangePass = findViewById(R.id.btnChangePass);
        txtSignIn = findViewById(R.id.txtChangePass);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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