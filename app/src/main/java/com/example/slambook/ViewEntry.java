package com.example.slambook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ViewEntry extends AppCompatActivity {
    private Button logout;
    private TextView username;

    private DatabaseReference rbRef;

    MainActivity usern = new MainActivity();
    String user = usern.username_;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        username = findViewById(R.id.profileUsername);
        logout = findViewById(R.id.logout);



        username.setText(user);
        //view profile change password and secret question

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfile = new Intent(getApplicationContext(), ChangePasswordOrSecretQ.class);
                gotoProfile.putExtra("Username", user);
                startActivity(gotoProfile);
            }
        });


    }
}