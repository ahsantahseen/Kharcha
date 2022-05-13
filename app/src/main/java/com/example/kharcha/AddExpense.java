package com.example.kharcha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class AddExpense extends AppCompatActivity {
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void goToMainMenu(View view){
        Intent startActivity = new Intent(this, MainActivity.class);
        startActivity(startActivity);
    }


}