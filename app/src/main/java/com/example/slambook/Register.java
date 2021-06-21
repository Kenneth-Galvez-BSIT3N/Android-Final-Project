package com.example.slambook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText fullNameField, usernameField, passwordField, conPasswordField, answerField;
    private ImageButton btnRegister, btnCancel;
    private TextView txtSignIn;
    private Spinner spnrQuestion;

    private RegisterFunctions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        functions = new RegisterFunctions();
        fullNameField = findViewById(R.id.edtFullName);
        usernameField = findViewById(R.id.edtUsername);
        passwordField = findViewById(R.id.edtPassword);
        conPasswordField = findViewById(R.id.edtConPassword);
        answerField = findViewById(R.id.edtAnswer);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancelRed);
        txtSignIn = findViewById(R.id.txtSignIn);
        spnrQuestion = findViewById(R.id.spnrQuestion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.questions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrQuestion.setAdapter(adapter);
        spnrQuestion.setOnItemSelectedListener(this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functions.Register(getApplicationContext());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}