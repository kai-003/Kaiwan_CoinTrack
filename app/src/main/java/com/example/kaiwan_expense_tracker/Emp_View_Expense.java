package com.example.kaiwan_expense_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Emp_View_Expense extends AppCompatActivity {

    private final DBHelper db = new DBHelper(this);

    ListView emp_exp_list;
    TextView emp_exp;
    ArrayAdapter<String> receiptAdapter;
    ImageButton view_back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_view_expense);
        Intent v=getIntent();
        String user=v.getStringExtra("user");
        emp_exp_list = findViewById(R.id.emp_expense_list);
        emp_exp = findViewById(R.id.emp_exp);
        view_back=findViewById(R.id.view_back);

        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d= new Intent(Emp_View_Expense.this, Employee_Dashboard.class);
                d.putExtra("user",user);
                startActivity(d);
            }
        });
        showReceiptForEmployee(user);
    }

    private void showReceiptForEmployee(String employeeName) {
        ArrayList<String> employeeReceipts = db.getAllExpensesForEmployee(employeeName);

        receiptAdapter = new ArrayAdapter<String>(this, R.layout.list_item_receipt, R.id.receiptText, employeeReceipts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Button deleteButton = view.findViewById(R.id.deleteButton);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String receiptInfo = getItem(position);
                        String[] parts = receiptInfo.split(":");
                        String expenseName = parts[0].trim();
                        float expenseValue = Float.parseFloat(parts[1].trim());
                        boolean isRemoved = db.removeData(employeeName, expenseName, expenseValue);

                        if (isRemoved) {
                            remove(getItem(position));
                            notifyDataSetChanged();
                            emp_exp.setText(String.valueOf(db.getTotalExpenseForEmployee(employeeName)));
                            Toast.makeText(Emp_View_Expense.this, "Record removed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Emp_View_Expense.this, "Error removing expense", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return view;
            }
        };

        emp_exp_list.setAdapter(receiptAdapter);
        emp_exp.setText(String.valueOf(db.getTotalExpenseForEmployee(employeeName)));
    }
}
