package com.example.parking;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setTitle("Park-X");
        actionBar.setSubtitle("Parking Made Easy");

        actionBar.setDisplayShowHomeEnabled(true);
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), registration.class);
            startActivity(i);
        });

        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener((View view) -> {

            Intent i = new Intent(getApplicationContext(), aboutUs.class);
            startActivity(i);
        });
        Button button3 = findViewById(R.id.button4);
        button3.setOnClickListener(view -> {
            String url = "https://park-x-4485e.web.app/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);

        });

        ImageView iv1 = findViewById(R.id.thirumalai);
        ImageView iv2 = findViewById(R.id.tharun);
        ImageView iv3 = findViewById(R.id.sivanmani);
        ImageView iv4 = findViewById(R.id.shylander);
        iv1.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), thiru.class);
            startActivity(i);
        });
        iv2.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), tharun.class);
            startActivity(i);
        });
        iv3.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), sivanmani.class);
            startActivity(i);
        });
        iv4.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), shylander.class);
            startActivity(i);
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // Function runs every MINUTES minutes.
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "0").setContentTitle("Next Hour").setContentText("MOBILE LAB");
                builder.setSmallIcon(R.drawable.home);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel mChannel = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mChannel = new NotificationChannel("0", "0", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(mChannel);
                }
                manager.notify(0, builder.build()); // If the function you wanted was static
            }
        }, 0, 1000 * 100);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.overflow, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent homeIntent = new Intent(getApplicationContext(), RFID.class);
                startActivity(homeIntent);
                break;
            case R.id.refresh:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            case R.id.poll:
                Intent Pollintent = new Intent(getApplicationContext(), PollActivity.class);
                startActivity(Pollintent);
                break;
            case R.id.about_us_menu:
                Intent i = new Intent(getApplicationContext(), aboutUs.class);
                startActivity(i);
                break;
            case R.id.our_site_menu:
                String url = "https://park-x-4485e.web.app/";
                Intent ii = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(ii);
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.setRemainder:
                Intent iii = new Intent(getApplicationContext(), RemainderActivity.class);
                startActivity(iii);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}