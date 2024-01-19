package com.example.kaiwan_expense_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Contact extends AppCompatActivity {

    ImageButton back_con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Intent v=getIntent();
        String user=v.getStringExtra("user");

        TextView emailTextView = findViewById(R.id.email);
        TextView phoneTextView = findViewById(R.id.phone);
        back_con = findViewById(R.id.con_back);

        back_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d= new Intent(Contact.this, Employee_Dashboard.class);
                d.putExtra("user",user);
                startActivity(d);
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString();
                openEmailApp(email);
            }
        });

        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneTextView.getText().toString();
                openPhoneApp(phoneNumber);
            }
        });
    }

    private void openEmailApp(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openPhoneApp(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
