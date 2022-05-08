package com.example.kharcha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        TextView textView = findViewById(R.id.signUpRoute);
        SpannableString content = new SpannableString("Don't have an account ?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }
    public void goToSignUp(View view){
        Intent startActivity = new Intent(this, SignUpScreen.class);
        startActivity(startActivity);
    }
    public void goToHome(View view){
        Intent startActivity = new Intent(this, MainActivity.class);
        startActivity(startActivity);
        finish();
    }
}