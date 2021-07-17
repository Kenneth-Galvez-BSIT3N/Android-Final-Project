package com.example.slambook;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText fullNameField, usernameField, passwordField, conPasswordField, answerField;
    private ImageButton btnRegister, btnCancel;
    private TextView txtSignIn;
    private Spinner spnrQuestion;

    private RegisterFunctions functions;

    private DatabaseReference dbRef;

    private User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        dbRef =  FirebaseDatabase.getInstance().getReference();
        functions = new RegisterFunctions();
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancelRed);
        txtSignIn = findViewById(R.id.txtSignIn);

        spnrQuestion = findViewById(R.id.spnrQuestion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.questions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrQuestion.setAdapter(adapter);
        spnrQuestion.setOnItemSelectedListener(Register.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullNameField = (EditText) findViewById(R.id.edtFullName);
                usernameField = findViewById(R.id.edtUsername);
                passwordField = findViewById(R.id.edtPassword);
                conPasswordField = findViewById(R.id.edtConPassword);
                answerField = findViewById(R.id.edtAnswer);

                EditText[] editTexts = {fullNameField, usernameField, passwordField, conPasswordField, answerField};

                boolean checker = true;
                for (EditText edt: editTexts) {
                    if(!checkIfEmpty(edt)){
                        checker = false;
                        break;
                    }
                }

                if(passwordField.getText().toString().equals(conPasswordField.getText().toString()) && checker){
                    newUser.setUsername(usernameField.getText().toString());
                    newUser.setPassword(passwordField.getText().toString());
                    newUser.setFullname(fullNameField.getText().toString());
                    newUser.setQuestion(spnrQuestion.getSelectedItem().toString());
                    newUser.setAnswer(answerField.getText().toString());


                    String id = newUser.getUsername().replaceAll("\\s+","_");
                    Toast.makeText(getApplicationContext(), newUser.getUsername(), Toast.LENGTH_SHORT).show();

                    dbRef.child("users").child(id).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Register.this, "Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Unsiccessful", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else if(!checker){
                    Toast.makeText(Register.this, "Fields must not be empty.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Register.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }


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
//        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean checkIfEmpty(EditText edt){
        if(!edt.getText().toString().equals(null) && !edt.getText().toString().equals("")){
            return true;
        }else{
            return false;
        }
    }
}