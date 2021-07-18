package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassword extends AppCompatActivity {
    private TextView username;
    private EditText oldPass, newPass, confirmPass;
    private Button confirm, cancel;

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();;

    MainActivity usern = new MainActivity();
    String user = usern.username_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();

    }

    private void init() {
        username = findViewById(R.id.cpUsername);
        oldPass = findViewById(R.id.cpOldPass);
        newPass = findViewById(R.id.newPass);
        confirmPass = findViewById(R.id.cpConfirmPass);
        confirm = findViewById(R.id.cpConfirm);
        cancel = findViewById(R.id.profileCancel);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFields()){
                    dbRef.child("users").child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.getResult().hasChild("password")){
                                if(task.getResult().child("password").getValue().toString().equals(oldPass.getText().toString())){
                                    dbRef.child("users").child(user).child("password").setValue(newPass.getText().toString());
                                    finish();
                                }else{
                                    Toast.makeText(ChangePassword.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(ChangePassword.this, "Error in password get", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    //Wrong Field
                    Toast.makeText(ChangePassword.this, "Fix All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private boolean checkAllFields(){
        boolean filled = true;
        EditText[] fields = new EditText[] {(EditText) findViewById(R.id.cpOldPass), (EditText)findViewById(R.id.newPass),(EditText)findViewById(R.id.cpConfirmPass)};

        for(int check =0 ; check < fields.length;check++){
            if(fields[check].getText().toString().equals(null)){
                filled = false;
                break;
            }
            if(!fields[1].getText().toString().equals(fields[2].getText().toString())){
                filled = false;
                break;
            }
        }
        return filled;
    }
}