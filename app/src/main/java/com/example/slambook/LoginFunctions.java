package com.example.slambook;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LoginFunctions {
    void Login(Context c, String username, String password){
        Toast.makeText(c, "USN: "+username+"\nPassword: "+password, Toast.LENGTH_SHORT).show();
    }

    void Cancel(Context c){
        Toast.makeText(c, "You clicked cancel.", Toast.LENGTH_SHORT).show();
    }

    void Forgot(Context c){
        Toast.makeText(c, "You clicked forgot password.", Toast.LENGTH_SHORT).show();
    }

    void SignUp(Context c){

    }
}
