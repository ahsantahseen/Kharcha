package com.example.kharcha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpScreen extends AppCompatActivity {
   EditText userName;
   EditText userPassword;
   EditText userEmail;
   EditText userDOB;
   Button registerBtn;

   FirebaseAuth auth;
   DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userEmail);
        userPassword=findViewById(R.id.userPassword);
        userDOB=findViewById(R.id.userDateOfBirth);
        registerBtn=findViewById(R.id.registerBtn);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
        registerBtn.setOnClickListener(view -> {
            createUser();
        });
    }
    public void createUser(){
        String email=userEmail.getText().toString();
        String password=userPassword.getText().toString();
        String dob=userDOB.getText().toString();
        String name=userName.getText().toString();
        if(TextUtils.isEmpty(name)){
            userName.setError("Name cannot be empty");
            userName.requestFocus();
        }
        else if(TextUtils.isEmpty(email)){
            userEmail.setError("Email cannot be empty");
            userEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(dob)){
            userDOB.setError("Date of Birth cannot be empty");
            userDOB.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            userPassword.setError("Password cannot be empty");
            userPassword.requestFocus();
        }else if(password.length()<6){
            userPassword.setError("Password cannot be less than 6 characters");
            userPassword.requestFocus();
        }else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user=new User(name,email,dob);
                        db.child("users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignUpScreen.this, "Thank you for creating an account! Login with your new account", Toast.LENGTH_SHORT).show();
                                    Intent startActivity = new Intent(SignUpScreen.this, LoginScreen.class);
                                    startActivity(startActivity);
                                    finish();
                                }else{
                                    Toast.makeText(SignUpScreen.this, "User Has Not Been Added To DB", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(SignUpScreen.this, "User Has Not Been Registered", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}