package com.example.kharcha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

    }
    public void goToMainMenu(View view){
        Intent startActivity = new Intent(this, MainActivity.class);
        startActivity(startActivity);
    }
}