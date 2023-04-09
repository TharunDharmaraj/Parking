package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RemainderActivity extends AppCompatActivity {
    TextView t2;
    DatePicker d;
    Button b, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Choose Date");
        t2 = findViewById(R.id.textView2);
        d = findViewById(R.id.datePicker);
        b = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        t2.setText("Current Date " + getCurrentDate());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t2.setText("Current Date " + getCurrentDate());
                Intent intent = new Intent(RemainderActivity.this, TimeActivity.class);
                String date = getCurrentDate();
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemainderActivity.this, TimeActivity.class);
                String date = getCurrentDate();
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

    public String getCurrentDate() {
        StringBuilder builder = new StringBuilder();
        builder.append((d.getMonth() + 1) + "/");
        builder.append(d.getDayOfMonth() + "/");
        builder.append(d.getYear());
        return builder.toString();
    }
}
