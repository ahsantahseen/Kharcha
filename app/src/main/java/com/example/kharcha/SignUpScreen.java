package com.example.kharcha;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class SignUpScreen extends AppCompatActivity {
   EditText userName;
   EditText userPassword;
   EditText userEmail;
   EditText userDOB;
   Button registerBtn;
   ProgressBar progressBar;

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
        progressBar=findViewById(R.id.signUpProgress);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
        registerBtn.setOnClickListener(view -> {
            createUser();
        });
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateCalendar();
            }
            @RequiresApi(api = Build.VERSION_CODES.N)
            private void updateCalendar(){
                userDOB.setText(DateFormat.getDateInstance().format(calendar.getTime()));
            }
        };
        userDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpScreen.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
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
        else if(!ExtraUtils.isValidEmail(email)){
            userEmail.setError("Please enter valid email");
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
            progressBar.setVisibility(View.VISIBLE);
            registerBtn.setFocusable(false);
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
                                    Expenses expenses = new Expenses(0, 0, 0, 0);
                                    db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Intent startActivity = new Intent(SignUpScreen.this, LoginScreen.class);
                                                startActivity(startActivity);
                                                finish();
                                            } else {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(SignUpScreen.this, "Error! Cannot Add Initial Amount", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(SignUpScreen.this, "User Has Not Been Added To DB", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignUpScreen.this, "User Has Not Been Registered", Toast.LENGTH_SHORT).show();
                    }
                    registerBtn.setFocusable(true);
                }
            });
        }

    }
}