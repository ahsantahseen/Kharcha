package com.example.kharcha;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference db;

    Button logOutBtn;
    int FoodAmount;
    int entertainmentAmount;
    int transportAmount;
    int healthAmount;

    TextView Food;
    TextView entertainment;
    TextView transport;
    TextView health;
    TextView total;
    TextView currentDate;
    NumberFormat formatter;
    ProgressBar progressBar;
    Pie pie;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formatter = new DecimalFormat("#,###");
        Food=findViewById(R.id.FoodValue);
        entertainment=findViewById(R.id.EntertainmentValue);
        transport=findViewById(R.id.TransportValue);
        health=findViewById(R.id.HealthValue);
        total=findViewById(R.id.TotalExpValue);
        currentDate=findViewById(R.id.currentDate);
        progressBar=findViewById(R.id.mainProgress);
        pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Food",0));
        data.add(new ValueDataEntry("Entertainment", 0));
        data.add(new ValueDataEntry("Health", 0));
        data.add(new ValueDataEntry("Transport", 0));
        pie.data(data);
        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
        logOutBtn=findViewById(R.id.logoutBtn);
        auth=FirebaseAuth.getInstance();
        logOutBtn.setOnClickListener(view -> {
            auth.signOut();
            Intent startActivity = new Intent(MainActivity.this, LoginScreen.class);
            startActivity(startActivity);
            finish();
        });
    }
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        db= FirebaseDatabase.getInstance().getReference();
        db.child("expenses").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.INVISIBLE);
                Expenses expenses=snapshot.getValue(Expenses.class);
                FoodAmount=expenses.food;
                entertainmentAmount=expenses.entertainment;
                healthAmount=expenses.health;
                transportAmount=expenses.transport;

                if(FoodAmount<=0&&entertainmentAmount<=0&&healthAmount<=0&&transportAmount<=0){
                    Toast.makeText(MainActivity.this, "Add Expenses to Populate the Chart", Toast.LENGTH_SHORT).show();
                }
                Food.setText(NumberFormat.getCurrencyInstance(new Locale("en_PK","PK")).format(FoodAmount));
                entertainment.setText(NumberFormat.getCurrencyInstance(new Locale("en_PK","PK")).format(entertainmentAmount));
                health.setText(NumberFormat.getCurrencyInstance(new Locale("en_PK","PK")).format(healthAmount));
                transport.setText(NumberFormat.getCurrencyInstance(new Locale("en_PK","PK")).format(transportAmount));
                total.setText(NumberFormat.getCurrencyInstance(new Locale("en_PK","PK")).format(expenses.totalExpenses));
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Food", FoodAmount));
                    data.add(new ValueDataEntry("Entertainment", entertainmentAmount));
                    data.add(new ValueDataEntry("Health", healthAmount));
                    data.add(new ValueDataEntry("Transport;", transportAmount));
                    pie.data(data);
                    Date date = new Date();
                    String stringDate = DateFormat.getDateInstance().format(date);
                    currentDate.setText(stringDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goToExpense(View view){
        Intent startActivity = new Intent(this, AddExpense.class);
        startActivity(startActivity);
    }
}