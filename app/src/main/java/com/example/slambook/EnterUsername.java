package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterUsername extends AppCompatActivity {

    ImageButton enterUSN;
    EditText edtTxtUSNInput;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_username);

        dbRef = FirebaseDatabase.getInstance().getReference();

        edtTxtUSNInput = findViewById(R.id.edtTxtUSNInput);

        enterUSN = findViewById(R.id.btnEnterUSN);

        enterUSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("users").child(edtTxtUSNInput.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.getResult().hasChild("question")){
                            String question = task.getResult().child("question").getValue().toString();
                            Intent gotoChangePW = new Intent(getApplicationContext(), AnswerQuestion.class);
                            gotoChangePW.putExtra("Question", question);
                            gotoChangePW.putExtra("Username", edtTxtUSNInput.getText().toString());
                            startActivity(gotoChangePW);
                            finish();
                        }else{
                            Toast.makeText(EnterUsername.this, "Username is not found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                Intent gotoChangePW = new Intent(getApplicationContext(), AnswerQuestion.class);
//                startActivity(gotoChangePW);
            }
        });

        findViewById(R.id.txtBacktoSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}