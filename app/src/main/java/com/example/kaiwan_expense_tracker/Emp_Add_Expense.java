package com.example.kaiwan_expense_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Emp_Add_Expense extends AppCompatActivity {
    private final DBHelper db = new DBHelper(this);
    EditText exp_nm, exp;
    Button confirm, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_add_expense);

        Intent a = getIntent();
        String usr = a.getStringExtra("user");
        exp_nm = findViewById(R.id.exp_nm);
        exp = findViewById(R.id.exp);
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ex_name = exp_nm.getText().toString();
                String expValue = exp.getText().toString();

                if (!expValue.isEmpty()) {
                    float ex = Float.parseFloat(expValue);
                    db.insertData(usr, ex_name, ex);
                    Toast.makeText(Emp_Add_Expense.this, "Expense recorded successfully!", Toast.LENGTH_SHORT).show();
                    Intent d = new Intent(Emp_Add_Expense.this, Employee_Dashboard.class);
                    d.putExtra("user", usr);
                    startActivity(d);
                } else {
                    Toast.makeText(Emp_Add_Expense.this, "Please enter a valid expense value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(Emp_Add_Expense.this, Employee_Dashboard.class);
                d.putExtra("user", usr);
                startActivity(d);
            }
        });
    }
}
