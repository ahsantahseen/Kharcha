package com.example.kharcha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //After two seconds the splash screen
        auth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = auth.getCurrentUser();
                Intent startActivity;
                if(user!=null){
                    startActivity = new Intent(SplashScreen.this, MainActivity.class);
                }
                else{
                    startActivity = new Intent(SplashScreen.this, LoginScreen.class);
                }
                startActivity(startActivity);
                finish();
            }
        }, 3500);
    }


}