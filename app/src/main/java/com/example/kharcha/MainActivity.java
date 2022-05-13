package com.example.kharcha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button logOutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("",12000));
        data.add(new ValueDataEntry("", 34300));
        data.add(new ValueDataEntry("", 18000));
        data.add(new ValueDataEntry("", 1000));
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


    public void goToExpense(View view){
        Intent startActivity = new Intent(this, AddExpense.class);
        startActivity(startActivity);
    }
}