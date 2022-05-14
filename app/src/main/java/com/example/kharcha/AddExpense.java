package com.example.kharcha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddExpense extends AppCompatActivity {
    DatabaseReference db;
    Spinner spinner;
    EditText amount;
    int FoodAmount;
    int entertainmentAmount;
    int transportAmount;
    int healthAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        spinner=findViewById(R.id.ExpenseSpinner);
        amount=findViewById(R.id.expenseAmount);

    }

    @Override
    protected void onStart() {
        super.onStart();
        db=FirebaseDatabase.getInstance().getReference();
        db.child("expenses").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Expenses expenses=snapshot.getValue(Expenses.class);
                FoodAmount=expenses.food;
                entertainmentAmount=expenses.entertainment;
                healthAmount=expenses.health;
                transportAmount=expenses.transport;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddExpense.this, "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goToMainMenu(View view){
        Intent startActivity = new Intent(this, MainActivity.class);
        startActivity(startActivity);
    }
    public void addExpense(View view){
        if(TextUtils.isEmpty(amount.getText().toString())){
            amount.setError("Amount cannot be empty");
            amount.requestFocus();
        }
        else {
            String value = spinner.getSelectedItem().toString();
            if (value.equalsIgnoreCase("food")) {
                FoodAmount = FoodAmount + Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Added Amount", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Add Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (value.equalsIgnoreCase("health")) {
                healthAmount = healthAmount + Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Added Amount", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Add Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (value.equalsIgnoreCase("transport")) {
                transportAmount = transportAmount + Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Added Amount", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Add Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (value.equalsIgnoreCase("entertainment")) {
                entertainmentAmount = entertainmentAmount + Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Added Amount", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Add Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Error While Adding", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void deductExpense(View view){
        if(TextUtils.isEmpty(amount.getText().toString())){
            amount.setError("Amount cannot be empty");
            amount.requestFocus();
        }
        else {
            String value = spinner.getSelectedItem().toString();
            if (value.equalsIgnoreCase("food") && Integer.parseInt(amount.getText().toString())<= FoodAmount) {
                FoodAmount = FoodAmount - Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Deducted Amount Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Deduct Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (value.equalsIgnoreCase("health") && Integer.parseInt(amount.getText().toString())<= healthAmount) {
                healthAmount = healthAmount - Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Deducted Amount Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Deduct Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (value.equalsIgnoreCase("transport") && Integer.parseInt(amount.getText().toString())<= transportAmount) {
                transportAmount = transportAmount - Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Deducted Amount Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Deduct Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (value.equalsIgnoreCase("entertainment") && Integer.parseInt(amount.getText().toString())<= transportAmount) {
                entertainmentAmount = entertainmentAmount - Integer.parseInt(amount.getText().toString());
                Expenses expenses = new Expenses(FoodAmount, healthAmount, transportAmount, entertainmentAmount);
                db.child("expenses").child(FirebaseAuth.getInstance().getUid()).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExpense.this, "Deducted Amount Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpense.this, "Error! Cannot Deduct Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Deduct Amount must be equal or less than actual amount", Toast.LENGTH_SHORT).show();
            }
        }

    }

}