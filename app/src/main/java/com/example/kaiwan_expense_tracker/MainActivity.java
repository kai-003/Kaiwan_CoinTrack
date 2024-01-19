package com.example.kaiwan_expense_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    EditText usr,pw;
    Button login,reset;
    final private String[][] userCredentials = {
            {"Kaiwan", "Kai123"},
            {"Anushree", "Anu456"},
            {"Ramesh", "Ram789"},
            {"Saswati", "Sasabc"},
            {"Adi", "adi888"}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usr=findViewById(R.id.usr_nm);
        pw=findViewById(R.id.pwd);
        login=findViewById(R.id.Login);
        reset=findViewById(R.id.Reset);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usrnm=usr.getText().toString();
                String pwd=pw.getText().toString();
                boolean isValidCredentials = false;
                for (String[] credentials : userCredentials) {
                    if (usrnm.equals(credentials[0]) && pwd.equals(credentials[1])) {
                        isValidCredentials = true;
                        break;
                    }
                }

                if (isValidCredentials) {
                    Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent dash=new Intent(MainActivity.this,Employee_Dashboard.class);
                    dash.putExtra("user",usrnm);
                    startActivity(dash);
                } else if (usrnm.equals("Admin") && pwd.equals("Admin123")) {
                    startActivity(new Intent(MainActivity.this,AdminActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr.setText("");
                pw.setText("");
            }
        });
    }
}