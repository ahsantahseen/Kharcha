package com.example.kharcha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    FirebaseAuth auth;

    EditText loginEmail;
    EditText loginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        TextView textView = findViewById(R.id.signUpRoute);
        SpannableString content = new SpannableString("Don't have an account ?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);
        auth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            Intent startActivity = new Intent(this, MainActivity.class);
            startActivity(startActivity);
            finish();
        }
    }

    public void goToSignUp(View view){
        Intent startActivity = new Intent(this, SignUpScreen.class);
        startActivity(startActivity);
    }
    public void goToHome(View view){
        String email=loginEmail.getText().toString();
        String password=loginPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            loginEmail.setError("Email cannot be empty");
            loginEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            loginPassword.setError("Password cannot be empty");
            loginPassword.requestFocus();
        }else if(password.length()<6) {
            loginPassword.setError("Password cannot be less than 6 characters");
            loginPassword.requestFocus();
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent startActivity = new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(startActivity);
                        finish();
                    }else{
                        Toast.makeText(LoginScreen.this, "Login Error! Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}