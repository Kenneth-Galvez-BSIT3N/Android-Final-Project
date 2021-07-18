package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordOrSecretQ extends AppCompatActivity {
    private Button changePass, changeSecretQ;
    private TextView username;

    MainActivity usern = new MainActivity();
    String user = usern.username_;

    User newUser = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_or_secret_q);

        init();
    }

    private void init(){
        username = findViewById(R.id.usernameText);
        changePass = findViewById(R.id.changeP);
        changeSecretQ = findViewById(R.id.changeQuestion);

        username.setText(user);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfileChangePassword = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(gotoProfileChangePassword);
            }
        });
        changeSecretQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfileChangeSecurityQuestion = new Intent(getApplicationContext(), ChangeSecurityQuestion.class);
                startActivity(gotoProfileChangeSecurityQuestion);
            }
        });

        findViewById(R.id.cpcsqCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}