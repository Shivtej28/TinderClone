package com.example.tinderclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChooseRegisterLoginActivity extends AppCompatActivity {

    TextView tv_signup;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_register_login);

        tv_signup = findViewById(R.id.signup);
        tv_signup.setPaintFlags(tv_signup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        user = FirebaseAuth.getInstance().getCurrentUser();
       if(user != null){
           Intent intent = new Intent(ChooseRegisterLoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void gotoRegister(View view) {
        Intent intent = new Intent(ChooseRegisterLoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    public void gotoLogin(View view) {
        Intent intent = new Intent(ChooseRegisterLoginActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}