package com.example.kaiwan_expense_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private final DBHelper db = new DBHelper(this);
    ListView receiptListView;
    Spinner empSpinner;
    TextView totalExpenseTextView;
    ArrayAdapter<String> receiptAdapter;
    ImageButton log;
    private String employeeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        log=findViewById(R.id.log);
        empSpinner = findViewById(R.id.emp);
        receiptListView = findViewById(R.id.Receipt);
        totalExpenseTextView = findViewById(R.id.expense);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(l);
                Toast.makeText(AdminActivity.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();

            }
        });
        String[] Employees = {"Select Employee", "Kaiwan", "Anushree", "Ramesh", "Saswati", "Adi", "All"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Employees);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empSpinner.setAdapter(adapter);

        empSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        clearReceipt();
                        break;
                    case 6:
                        employeeName = "All";
                        showReceiptForAll();
                        break;
                    default:
                        employeeName = Employees[i];
                        showReceiptForEmployee(employeeName);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case where nothing is selected
            }
        });
    }

    private void clearReceipt() {
        receiptAdapter = new ArrayAdapter<>(this, R.layout.list_item_receipt, new ArrayList<>());
        receiptListView.setAdapter(receiptAdapter);
        totalExpenseTextView.setText("0");
    }

    private void showReceiptForAll() {
        ArrayList<String> allReceipts = db.getAllExpensesForAllEmployees();

        receiptAdapter = new ArrayAdapter<String>(this, R.layout.list_item_receipt, R.id.receiptText, allReceipts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Button deleteButton = view.findViewById(R.id.deleteButton);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String receiptInfo = getItem(position);
                        String[] parts = receiptInfo.split(": ");

                        if (parts.length == 3 && isValidExpenseValue(parts[2].trim())) {
                            String employeeName = parts[0].trim();
                            String expenseName = parts[1].trim();
                            float expenseValue = Float.parseFloat(parts[2].trim());

                            boolean isRemoved = db.removeData(employeeName, expenseName, expenseValue);

                            if (isRemoved) {
                                remove(getItem(position));
                                notifyDataSetChanged();
                                totalExpenseTextView.setText(String.valueOf(db.getTotalExpenseForALLEmployees()));
                                Toast.makeText(AdminActivity.this, "Record removed successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AdminActivity.this, "Error removing expense", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AdminActivity.this, "Invalid receipt format or expense value", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                return view;
            }
        };

        receiptListView.setAdapter(receiptAdapter);
        totalExpenseTextView.setText(String.valueOf(db.getTotalExpenseForALLEmployees()));
    }

    private boolean isValidExpenseValue(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
                            totalExpenseTextView.setText(String.valueOf(db.getTotalExpenseForEmployee(employeeName)));
                            Toast.makeText(AdminActivity.this, "Record removed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdminActivity.this, "Error removing expense", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return view;
            }
        };

        receiptListView.setAdapter(receiptAdapter);
        totalExpenseTextView.setText(String.valueOf(db.getTotalExpenseForEmployee(employeeName)));
    }
}
