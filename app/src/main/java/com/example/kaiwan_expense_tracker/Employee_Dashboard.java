package com.example.kaiwan_expense_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Employee_Dashboard extends AppCompatActivity {
    TextView greet;
    CardView add, view, contact, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        add = findViewById(R.id.add_exp);
        view = findViewById(R.id.view_exp);
        contact = findViewById(R.id.contact);
        logout = findViewById(R.id.logout);

        Intent i = getIntent();
        String usr = i.getStringExtra("user");


        greet = findViewById(R.id.Greeting);
        greet.setText("Welcome, " + usr +"!");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(Employee_Dashboard.this, MainActivity.class);
                startActivity(l);
                Toast.makeText(Employee_Dashboard.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Employee_Dashboard.this, Emp_Add_Expense.class);
                a.putExtra("user",usr);
                startActivity(a);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(Employee_Dashboard.this, Emp_View_Expense.class);
                v.putExtra("user",usr);
                startActivity(v);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(Employee_Dashboard.this, Contact.class);
                startActivity(c);
            }
        });
    }
}
