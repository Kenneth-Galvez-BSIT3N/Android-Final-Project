package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.view.Change;

public class ChangeSecurityQuestion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView usernameTitle;
    private EditText answer, password;
    private Spinner questions;
    private Button proceed, cancel;
       Context c;
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    MainActivity usern = new MainActivity();
    String user = usern.username_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_security_question);

        init();
    }

    private void init() {
        usernameTitle = findViewById(R.id.csqUsername);
        answer = findViewById(R.id.csqAnswer);
        password = findViewById(R.id.csqPassword);
        proceed = findViewById(R.id.csqConfirm);
        cancel = findViewById(R.id.csqCancel);



        questions = findViewById(R.id.csqQuestion);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChangeSecurityQuestion.this ,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.questions));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questions.setAdapter(adapter);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String daQuestion = questions.getSelectedItem().toString();
                String ans = answer.getText().toString();
                if(checkAllFields()){
                    dbRef.child("users").child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.getResult().hasChild("password") && task.getResult().hasChild("question")){
                                if(task.getResult().child("password").getValue().toString().equals(password.getText().toString())){
                                    if(!task.getResult().child("question").getValue().toString().equals(daQuestion)){
                                        dbRef.child("users").child(user).child("question").setValue(daQuestion);
                                        dbRef.child("users").child(user).child("answer").setValue(ans);
                                        finish();
                                    }else{
                                        Toast.makeText(ChangeSecurityQuestion.this, "Pick Another Question", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(ChangeSecurityQuestion.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(ChangeSecurityQuestion.this, "Error in password get", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(ChangeSecurityQuestion.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
    }


    private boolean checkAllFields(){
        boolean filled = true;
        EditText[] fields = new EditText[] {(EditText) findViewById(R.id.csqAnswer), (EditText)findViewById(R.id.csqPassword)};

        for(int check =0 ; check < fields.length;check++){
            if(fields[check].getText().toString().equals(null)){
                filled = false;
                break;
            }
        }
        return filled;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}