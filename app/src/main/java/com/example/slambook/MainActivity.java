package com.example.slambook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usnField, pwField;
    private ImageButton btnLogin, btnCancel;
    private TextView txtForgotPass, txtSignUp;

    private LoginFunctions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
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
                functions.Login(getApplicationContext(), usnField.getText().toString(), pwField.getText().toString());
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
                Intent gotoChangePW = new Intent(getApplicationContext(), ForgotPass.class);
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