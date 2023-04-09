package com.example.parking;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;


public class TimeActivity extends AppCompatActivity {
    TextView t3;
    Button b3;
    TimePicker t;
    NotificationCompat.Builder notification;
    PendingIntent pIntent;
    TaskStackBuilder stackBuilder;
    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Choose Time");

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        t3 = findViewById(R.id.textView3);
        b3 = findViewById(R.id.button3);
        t = findViewById(R.id.timePicker);
        t.setIs24HourView(true);
        t3.setText("Current Time " + getCurrentTime());

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t3.setText("Current Time " + getCurrentTime());
                System.out.println(date + " " + getCurrentTime());
                String dateTime = date + " " + getCurrentTime();
                System.out.println(dateTime);
                Intent intent = new Intent(TimeActivity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Remainder Set at " + dateTime, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                Timer t = new Timer();
                try {
                    t.schedule(new TimerTask() {
                        public void run() {
                            startNotification();
                        }
                    }, new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(dateTime));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void startNotification() {
        notification = new NotificationCompat.Builder(TimeActivity.this, "0");
        notification.setContentTitle("Happy Birthday");

        notification.setContentText("To Tharun");
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setTicker("Happy Birthday");

        stackBuilder = TaskStackBuilder.create(TimeActivity.this);
        stackBuilder.addParentStack(RemainderActivity.class);


        Intent intent = new Intent(TimeActivity.this, RemainderActivity.class);
        stackBuilder.addNextIntent(intent);
        pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        notification.setContentIntent(pIntent);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0", "notify", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, notification.build());
    }

    public String getCurrentTime() {
        String time = "";
        time += t.getHour() + ":";
        time += t.getMinute();
        return time;
    }
}
