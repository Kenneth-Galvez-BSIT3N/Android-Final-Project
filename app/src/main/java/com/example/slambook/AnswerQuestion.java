package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnswerQuestion extends AppCompatActivity {

    TextView sec_question;

    private DatabaseReference dbRef;

    EditText edtYourQuestion;

    String question, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

        dbRef = FirebaseDatabase.getInstance().getReference();

        sec_question = findViewById(R.id.sec_question);

        Intent intent = getIntent();
        question = intent.getStringExtra("Question");
        username = intent.getStringExtra("Username");

        sec_question.setText(question);

        edtYourQuestion = findViewById(R.id.edtYourQuestion);


        findViewById(R.id.btnEnterAns).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.getResult().child("answer").getValue().equals(edtYourQuestion.getText().toString())){
                            Intent gotoChangePW = new Intent(getApplicationContext(), ForgotPass.class);
                            gotoChangePW.putExtra("Username", username);
                            startActivity(gotoChangePW);
                            finish();
                        }else{
                            Toast.makeText(AnswerQuestion.this, "Incorrect answer.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//
            }
        });

        findViewById(R.id.txtBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}